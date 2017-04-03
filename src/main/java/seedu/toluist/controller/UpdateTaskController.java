//@@author A0127545A
package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;
import seedu.toluist.ui.commons.ResultMessage;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_ERROR_DUPLICATED_TASK =
            "Task provided already exist in the list.";
    private static final String RESULT_MESSAGE_ERROR_INVALID_INDEX =
            "No valid index found.";
    private static final String RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK =
            "The task cannot be classified as a floating task, deadline, or event.";
    private static final String RESULT_MESSAGE_ERROR_RECURRING_AND_STOP_RECURRING =
            "Input contains both recurring and stop recurring arguments at the same time.";
    private static final String RESULT_MESSAGE_ERROR_FLOATING_AND_NON_FLOATING =
            "Input contains both floating and non-floating task arguments at the same time.";
    private static final String RESULT_MESSAGE_ERROR_CLONING_ERROR =
            "Bad things happened and we have no idea why! Please contact the administrators.";

  //@@author A0162011A
    private static final String HELP_DETAILS = "Updates an existing task in the todo list.";
    private static final String HELP_FORMAT = "update INDEX [NAME] [from/STARTDATE to/ENDDATE] "
                                                        + "[by/ENDDATE] [repeat/PERIOD(daily/weekly/monthly)] "
                                                        + "[priority/PRIORITY(high/low)] [tags/TAGS]";
    private static final String[] HELP_COMMENTS = { "Related commands: `add`, `delete`",
                                                    "Only fields entered will be updated.",
                                                    "When editing tags, the existing tags "
                                                        + "will be replaced with the new tags.",
                                                    "Events can be changed to deadlines by using `by/`",
                                                    "Deadlines can be changed to events by using `from/` and `to/`" };
    private static final String[] HELP_EXAMPLES = { "`update 1 new name`\nUpdates the name "
                                                        + "for the floating task at index 1 to `new name`.",
                                                    "`update 2 from/today to/tomorrow`\n"
                                                        + "Updates the start and end time of the event at index 2.",
                                                    "`update 3 by/sunday`\n"
                                                        + "Updates the end time of the deadline at index 3.",
                                                    "`update 4 repeat/daily by/9pm`\nUpdates the task "
                                                        + "at index 4 to repeat every day with a deadline of 9pm.",
                                                    "`update 5 priority/high`\n"
                                                        + "Updates the priority of the task at index 5 to high.",
                                                    "`update 6 tags/newtag`\n"
                                                        + "Updates the tags for the task at index 6 to `newtag`." };

    //@@author A0127545A
    private static final Logger logger = LogsCenter.getLogger(UpdateTaskController.class);

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        CommandResult commandResult;

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.PARAMETER_TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());
        if (indexes == null || indexes.isEmpty()) {
            uiStore.setCommandResult(new CommandResult(RESULT_MESSAGE_ERROR_INVALID_INDEX));
            return;
        }
        List<Task> shownTasks = uiStore.getShownTasks(indexes);
        Task task = shownTasks.get(0);

        String eventStartDateToken = tokens.get(TaskTokenizer.KEYWORD_EVENT_START_DATE);
        LocalDateTime eventStartDateTime = DateTimeUtil.parseDateString(eventStartDateToken);

        String eventEndDateToken = tokens.get(TaskTokenizer.KEYWORD_EVENT_END_DATE);
        LocalDateTime eventEndDateTime = DateTimeUtil.parseDateString(eventEndDateToken);

        String taskDeadlineToken = tokens.get(TaskTokenizer.KEYWORD_TASK_DEADLINE);
        LocalDateTime taskDeadline = DateTimeUtil.parseDateString(taskDeadlineToken);

        boolean isFloating = tokens.containsKey(TaskTokenizer.KEYWORD_TASK_FLOATING);

        String taskPriority = tokens.get(TaskTokenizer.KEYWORD_TASK_PRIORITY);

        String tagsToken = tokens.get(TaskTokenizer.KEYWORD_TASK_TAGS);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        String recurringFrequency = tokens.get(TaskTokenizer.KEYWORD_TASK_RECURRING_FREQUENCY);

        String recurringUntilEndDateToken = tokens.get(TaskTokenizer.KEYWORD_TASK_RECURRING_UNTIL_END_DATE);
        LocalDateTime recurringUntilEndDate = DateTimeUtil.parseDateString(recurringUntilEndDateToken);

        boolean isStopRecurring = tokens.containsKey(TaskTokenizer.KEYWORD_TASK_STOP_RECURRING);

        commandResult = update(task, description, eventStartDateTime, eventEndDateTime,
                taskDeadline, isFloating, taskPriority, tags,
                recurringFrequency, recurringUntilEndDate, isStopRecurring);

        uiStore.setCommandResult(commandResult);
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, true);
    }

    private CommandResult update(Task task, String description,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime, LocalDateTime taskDeadline,
            boolean isFloating, String taskPriority, Set<Tag> tags,
            String recurringFrequency, LocalDateTime recurringUntilEndDate, boolean isStopRecurring) {
        try {
            validateTaskDatesInput(eventStartDateTime, eventEndDateTime, taskDeadline, isFloating);
            validateTaskRecurringStatusInput(recurringFrequency, recurringUntilEndDate, isStopRecurring);
            validateTaskFloatingStatusInput(eventStartDateTime, eventEndDateTime, taskDeadline, isFloating);

            Task taskCopy = (Task) task.clone();
            taskCopy = updateTaskDates(taskCopy, isFloating, eventStartDateTime, eventEndDateTime, taskDeadline);
            taskCopy = updateTaskDescription(taskCopy, description);
            taskCopy = updateTaskPriority(taskCopy, taskPriority);
            taskCopy = updateTaskRecurringFrequency(taskCopy, recurringFrequency);
            taskCopy = updateTaskRecurringUntilEndDate(taskCopy, recurringUntilEndDate);
            taskCopy = updateTaskTags(taskCopy, tags);
            taskCopy = updateTaskUnsetRecurringStatus(taskCopy, isStopRecurring);

            TodoList todoList = TodoList.getInstance();
            validatesNoDuplicateTask(taskCopy, todoList);

            // Update all changes in taskCopy to task
            Task oldTask = (Task) task.clone();
            task.setTask(taskCopy);

            updateTaskInTodoList(task, todoList);
            return new CommandResult(ResultMessage.getUpdateCommandResultMessage(oldTask, task, uiStore));
        } catch (IllegalArgumentException illegalArgumentException) {
            return new CommandResult(illegalArgumentException.getMessage());
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return new CommandResult(RESULT_MESSAGE_ERROR_CLONING_ERROR);
        }
    }

    /**
     * Checks whether the user input for dates is valid.
     * The input is valid if there is only one possible task type, or zero (task type is unchanged).
     * @param eventStartDateTime
     * @param eventEndDateTime
     * @param taskDeadline
     * @param isFloating
     * @throws IllegalArgumentException if there is more than 1 possible task type
     */
    private void validateTaskDatesInput(LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline, boolean isFloating) throws IllegalArgumentException {
        int numberOfTaskTypes = 0;
        // Can update event start date time OR end date time
        if (eventStartDateTime != null || eventEndDateTime != null) {
            numberOfTaskTypes++;
        }
        if (taskDeadline != null) {
            numberOfTaskTypes++;
        }
        if (isFloating) {
            numberOfTaskTypes++;
        }
        if (numberOfTaskTypes > 1) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_UNCLASSIFIED_TASK);
        }
    }

    /**
     * Check that there is no recurring task arguments together with non-recurring task arguments
     * @param recurringFrequency
     * @param recurringUntilEndDate
     * @param isStopRecurring
     * @throws IllegalArgumentException if both recurring and non-recurring task arguments are present
     */
    private void validateTaskRecurringStatusInput(String recurringFrequency, LocalDateTime recurringUntilEndDate,
            boolean isStopRecurring) throws IllegalArgumentException {
        if (isStopRecurring && (StringUtil.isPresent(recurringFrequency) || recurringUntilEndDate != null)) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_RECURRING_AND_STOP_RECURRING);
        }
    }

    /**
     * Check that there is no floating task arguments together with non-floating task arguments
     * @param eventStartDateTime
     * @param eventEndDateTime
     * @param taskDeadline
     * @param isFloating
     * @throws IllegalArgumentException if both floating and non-floating task arguments are present
     */
    private void validateTaskFloatingStatusInput(LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline, boolean isFloating) throws IllegalArgumentException {
        if (isFloating && (eventStartDateTime != null || eventEndDateTime != null || taskDeadline != null)) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_FLOATING_AND_NON_FLOATING);
        }
    }

    private Task updateTaskDates(Task task, boolean isFloating,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime, LocalDateTime taskDeadline) {
        if (isFloating) {
            task.setStartDateTime(null);
            task.setEndDateTime(null);
        } else if (taskDeadline != null) {
            task.setStartDateTime(null);
            task.setEndDateTime(taskDeadline);
        } else {
            if (eventStartDateTime != null) {
                task.setStartDateTime(eventStartDateTime);
            }
            if (eventEndDateTime != null) {
                task.setEndDateTime(eventEndDateTime);
            }
        }
        return task;
    }

    private Task updateTaskDescription(Task task, String description) {
        if (StringUtil.isPresent(description)) {
            task.setDescription(description);
        }
        return task;
    }

    private Task updateTaskPriority(Task task, String taskPriority) {
        if (StringUtil.isPresent(taskPriority)) {
            task.setTaskPriority(taskPriority);
        }
        return task;
    }

    private Task updateTaskRecurringFrequency(Task task, String recurringFrequency) {
        if (StringUtil.isPresent(recurringFrequency)) {
            task.setRecurringFrequency(recurringFrequency);
        }
        return task;
    }

    private Task updateTaskRecurringUntilEndDate(Task task, LocalDateTime recurringUntilEndDate) {
        if (recurringUntilEndDate != null) {
            task.setRecurringEndDateTime(recurringUntilEndDate);
        }
        return task;
    }

    private Task updateTaskTags(Task task, Set<Tag> tags) {
        if (!tags.isEmpty()) {
            task.replaceTags(tags);
        }
        return task;
    }

    private Task updateTaskUnsetRecurringStatus(Task task, boolean isStopRecurring) {
        if (isStopRecurring) {
            task.unsetRecurring();
        }
        return task;
    }

    private void validatesNoDuplicateTask(Task task, TodoList todoList) throws IllegalArgumentException {
        if (todoList.getTasks().contains(task)) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_DUPLICATED_TASK);
        }
    }

    private void updateTaskInTodoList(Task task, TodoList todoList) {
        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks(), task);
        }
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
