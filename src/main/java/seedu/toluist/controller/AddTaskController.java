//@@author A0127545A
package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;
import seedu.toluist.ui.commons.ResultMessage;

/**
 * AddTaskController is responsible for adding a task (and event)
 */
public class AddTaskController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(AddTaskController.class);

    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*add"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_ADD_TASK = "add";

    private static final String RESULT_MESSAGE_ERROR_EMPTY_DESCRIPTION =
            "Please provide a description.";
    private static final String RESULT_MESSAGE_ERROR_DUPLICATED_TASK =
            "Task provided already exist in the list.";
    private static final String RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK =
            "The task cannot be classified as a floating task, deadline, or event.";
    private static final String RESULT_MESSAGE_ERROR_EVENT_MUST_HAVE_START_AND_END_DATE =
            "An event must have both a start date (from/) and an end date (to/).";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Adds a task to the todo list.";
    private static final String HELP_FORMAT = "add NAME [from/STARTDATE to/ENDDATE] "
                                                  + "[by/ENDDATE] [repeat/PERIOD"
                                                    + "(daily/weekly/monthly/yearly)] "
                                                  + "[priority/PRIORITY(high/low)] [tags/TAGS]";
    private static final String[] HELP_COMMENTS = { "Related commands: `delete`, `update`",
                                                    "Only fields entered will be used.",
                                                    "Events can be created by entering a value for `by/`",
                                                    "Deadlines can be created by "
                                                        + "entering a value for `from/` and `to/`" };
    private static final String[] HELP_EXAMPLES = { "`add new floating task`\nAdds a new floating task.",
                                                    "`add new deadline by/friday`\n"
                                                        + "Adds a new deadline, with deadline friday.",
                                                    "`add new event from/tuesday to/thursday`\nAdds a new event, "
                                                        + "with start date tuesday and end date thursday.",
                                                    "`add new recurring task by/10pm repeat/daily`\n"
                                                        + "Adds a new recurring task, with deadline 10pm.",
                                                    "`add new tagged task tags/newtag`\n"
                                                        + "Adds a new task, with the tag `newtag`.",
                                                    "`add new priority task priority/high`\n"
                                                        + "Adds a new task, with high priority." };

    //@@author A0127545A
    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        CommandResult commandResult;

        String description = tokens.get(TaskTokenizer.PARAMETER_TASK_DESCRIPTION);

        String eventStartDateToken = tokens.get(TaskTokenizer.KEYWORD_EVENT_START_DATE);
        LocalDateTime eventStartDateTime = DateTimeUtil.parseDateString(eventStartDateToken);

        String eventEndDateToken = tokens.get(TaskTokenizer.KEYWORD_EVENT_END_DATE);
        LocalDateTime eventEndDateTime = DateTimeUtil.parseDateString(eventEndDateToken);

        String taskDeadlineToken = tokens.get(TaskTokenizer.KEYWORD_TASK_DEADLINE);
        LocalDateTime taskDeadline = DateTimeUtil.parseDateString(taskDeadlineToken);

        String tagsToken = tokens.get(TaskTokenizer.KEYWORD_TASK_TAGS);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        String taskPriority = tokens.get(TaskTokenizer.KEYWORD_TASK_PRIORITY);

        String recurringFrequency = tokens.get(TaskTokenizer.KEYWORD_TASK_RECURRING_FREQUENCY);

        String recurringUntilEndDateToken = tokens.get(TaskTokenizer.KEYWORD_TASK_RECURRING_UNTIL_END_DATE);
        LocalDateTime recurringUntilEndDate = DateTimeUtil.parseDateString(recurringUntilEndDateToken);

        commandResult = add(todoList, description, eventStartDateTime, eventEndDateTime,
                taskDeadline, taskPriority, tags, recurringFrequency, recurringUntilEndDate);
        uiStore.setCommandResult(commandResult);
    }

    public Map<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, false, true);
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline, String taskPriority, Set<Tag> tags,
            String recurringFrequency, LocalDateTime recurringUntilEndDate) throws InvalidCommandException {
        try {
            validateTaskDescription(description);
            validateTaskDatesInput(eventStartDateTime, eventEndDateTime, taskDeadline);
            Task task = createTask(null, description, eventStartDateTime, eventEndDateTime, taskDeadline);
            task = addPriorityToTask(task, taskPriority);
            task = addRecurringStatusToTask(task, recurringFrequency, recurringUntilEndDate);
            task = addTagsToTask(task, tags);

            validatesNoDuplicateTask(task, todoList);
            addTaskToTodoList(task, todoList);
            return new CommandResult(ResultMessage.getAddCommandResultMessage(task, uiStore));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidCommandException(illegalArgumentException.getMessage());
        }
    }

    /**
     * Checks whether the user input for description is present
     * @param description
     * @throws IllegalArgumentException when there is no description
     */
    private void validateTaskDescription(String description) throws IllegalArgumentException {
        if (!StringUtil.isPresent(description)) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_EMPTY_DESCRIPTION);
        }
    }

    /**
     * Checks whether the user input for dates is valid (belongs to only one type of task)
     * @param eventStartDateTime
     * @param eventEndDateTime
     * @param taskDeadline
     * @throws IllegalArgumentException when the input for dates is invalid
     */
    private void validateTaskDatesInput(LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline) throws IllegalArgumentException {
        if (eventStartDateTime != null && eventEndDateTime != null) {
            if (taskDeadline != null) {
                throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK);
            } // else it is a valid event
        } else if (eventStartDateTime != null || eventEndDateTime != null) {
            if (taskDeadline != null) {
                throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK);
            } else {
                throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_EVENT_MUST_HAVE_START_AND_END_DATE);
            }
        } // else it is a valid task (floating or deadline)
    }

    private Task createTask(Task task, String description, LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime, LocalDateTime taskDeadline) throws IllegalArgumentException {
        if (eventStartDateTime != null && eventEndDateTime != null) {
            task = new Task(description, eventStartDateTime, eventEndDateTime);
        } else if (taskDeadline != null) {
            task = new Task(description, taskDeadline);
        } else if (eventStartDateTime == null && eventEndDateTime == null && taskDeadline == null) {
            task = new Task(description);
        } else {
            // should not reach here since it will fail validation at the top
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK);
        }
        return task;
    }

    private Task addPriorityToTask(Task task, String taskPriority) {
        if (taskPriority != null) {
            task.setTaskPriority(taskPriority);
        }
        return task;
    }

    private Task addRecurringStatusToTask(Task task,
            String recurringFrequency, LocalDateTime recurringUntilEndDate) {
        if (StringUtil.isPresent(recurringFrequency)) {
            if (recurringUntilEndDate == null) {
                task.setRecurring(recurringFrequency);
            } else {
                task.setRecurring(recurringUntilEndDate, recurringFrequency);
            }
        }
        return task;
    }

    private Task addTagsToTask(Task task, Set<Tag> tags) {
        task.replaceTags(tags);
        return task;
    }

    private void validatesNoDuplicateTask(Task task, TodoList todoList) throws IllegalArgumentException {
        if (todoList.getTasks().contains(task)) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_DUPLICATED_TASK);
        }
    }

    private void addTaskToTodoList(Task task, TodoList todoList) throws IllegalArgumentException {
        todoList.add(task);
        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks(), task);
        }
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_ADD_TASK };
    }

    public Map<String, String[]> getCommandKeywordMap() {
        String[] keywords = new String[] {
            TaskTokenizer.KEYWORD_EVENT_END_DATE,
            TaskTokenizer.KEYWORD_EVENT_START_DATE,
            TaskTokenizer.KEYWORD_TASK_DEADLINE,
            TaskTokenizer.KEYWORD_TASK_PRIORITY,
            TaskTokenizer.KEYWORD_TASK_RECURRING_FREQUENCY,
            TaskTokenizer.KEYWORD_TASK_RECURRING_UNTIL_END_DATE,
            TaskTokenizer.KEYWORD_TASK_TAGS
        };
        HashMap<String, String[]> keywordMap = new HashMap<>();
        for (String keyword : keywords) {
            keywordMap.put(keyword, new String[0]);
        }
        keywordMap.put(TaskTokenizer.KEYWORD_TASK_PRIORITY,
                new String[] { Task.HIGH_PRIORITY_STRING, Task.LOW_PRIORITY_STRING });
        keywordMap.put(TaskTokenizer.KEYWORD_TASK_RECURRING_FREQUENCY,
                new String[] {
                    Task.RecurringFrequency.DAILY.name(), Task.RecurringFrequency.WEEKLY.name(),
                    Task.RecurringFrequency.MONTHLY.name(), Task.RecurringFrequency.YEARLY.name()
                });
        return keywordMap;
    }

    public String[][][] getConflictingKeywordsList() {
        return new String[][][] { new String[][] {
            new String[] { TaskTokenizer.KEYWORD_EVENT_START_DATE, TaskTokenizer.KEYWORD_EVENT_END_DATE },
            new String[] { TaskTokenizer.KEYWORD_TASK_DEADLINE }
        }};
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
