package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.Task.TaskPriority;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * AddTaskController is responsible for adding a task (and event)
 */
public class AddTaskController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(AddTaskController.class);

    private static final String COMMAND_TEMPLATE = "^add"
            + "(\\s+(?<description>.+))?";

    private static final String COMMAND_ADD_TASK = "add";

    private static final String RESULT_MESSAGE_ADD_TASK = "New task added";
    private static final String RESULT_MESSAGE_ERROR_DATE_INPUT = "Something is wrong with the given dates input";

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        CommandResult commandResult;

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String eventStartDateToken = tokens.get(TaskTokenizer.EVENT_START_DATE_KEYWORD);
        LocalDateTime eventStartDateTime = DateTimeUtil.parseDateString(eventStartDateToken);

        String eventEndDateToken = tokens.get(TaskTokenizer.EVENT_END_DATE_KEYWORD);
        LocalDateTime eventEndDateTime = DateTimeUtil.parseDateString(eventEndDateToken);

        String taskDeadlineToken = tokens.get(TaskTokenizer.TASK_DEADLINE_KEYWORD);
        LocalDateTime taskDeadline = DateTimeUtil.parseDateString(taskDeadlineToken);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        Optional<Boolean> isHighPriority = Optional.empty();
        if (tokens.containsKey(TaskTokenizer.TASK_PRIORITY_KEYWORD)) {
            String taskPriority = tokens.get(TaskTokenizer.TASK_PRIORITY_KEYWORD);
            isHighPriority = Optional.of(taskPriority.equals("high"));
        }

        commandResult = add(todoList, description, eventStartDateTime, eventEndDateTime,
                taskDeadline, isHighPriority, tags);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        uiStore.setCommandResult(commandResult);
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, false, true);
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline, Optional<Boolean> isHighPriority, Set<Tag> tags) {
        if (!isValidTaskType(eventStartDateTime, eventEndDateTime, taskDeadline)) {
            return new CommandResult(RESULT_MESSAGE_ERROR_DATE_INPUT);
        }

        Task task;
        if (eventStartDateTime != null && eventEndDateTime != null) {
            task = new Task(description, eventStartDateTime, eventEndDateTime);
        } else if (taskDeadline != null) {
            task = new Task(description, taskDeadline);
        } else {
            task = new Task(description);
        }
        if (isHighPriority.isPresent()) {
            TaskPriority priorityLevel = (isHighPriority.get()) ? TaskPriority.HIGH : TaskPriority.LOW;
            task.setTaskPriority(priorityLevel);
        }
        task.replaceTags(tags);
        todoList.add(task);
        return new CommandResult(RESULT_MESSAGE_ADD_TASK);
    }

    /**
     * Checks whether the user input for dates is valid.
     * The input is valid if there is only one possible task type, or zero (task type is floating task).
     * @param eventStartDateTime
     * @param eventEndDateTime
     * @param taskDeadline
     * @return true if there is at most 1 possible task type
     */
    private boolean isValidTaskType(LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline) {
        int numberOfTaskTypes = 0;
        // Must have both event start date time AND end date time to be valid event
        if (eventStartDateTime != null && eventEndDateTime != null) {
            numberOfTaskTypes++;
        }
        if (taskDeadline != null) {
            numberOfTaskTypes++;
        }
        return numberOfTaskTypes <= 1;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_ADD_TASK };
    }
}
