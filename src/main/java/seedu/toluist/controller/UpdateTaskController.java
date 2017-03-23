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
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";
    private static final String RESULT_MESSAGE_ERROR_DATE_INPUT = "Something is wrong with the given dates input";

    private static final Logger logger = LogsCenter.getLogger(UpdateTaskController.class);

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());
        Task task = uiStore.getShownTasks(indexes).get(0);

        String eventStartDateToken = tokens.get(TaskTokenizer.EVENT_START_DATE_KEYWORD);
        LocalDateTime eventStartDateTime = DateTimeUtil.parseDateString(eventStartDateToken);

        String eventEndDateToken = tokens.get(TaskTokenizer.EVENT_END_DATE_KEYWORD);
        LocalDateTime eventEndDateTime = DateTimeUtil.parseDateString(eventEndDateToken);

        String taskDeadlineToken = tokens.get(TaskTokenizer.TASK_DEADLINE_KEYWORD);
        LocalDateTime taskDeadline = DateTimeUtil.parseDateString(taskDeadlineToken);

        boolean isFloating = tokens.containsKey(TaskTokenizer.TASK_FLOATING_KEYWORD);

        String taskPriority = tokens.get(TaskTokenizer.TASK_PRIORITY_KEYWORD);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        commandResult = update(task, description, eventStartDateTime, eventEndDateTime,
                taskDeadline, isFloating, taskPriority, tags);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, true);
    }

    private CommandResult update(Task task, String description,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime, LocalDateTime taskDeadline,
            boolean isFloating, String taskPriority, Set<Tag> tags) {
        if (!isValidTaskType(eventStartDateTime, eventEndDateTime, taskDeadline, isFloating)) {
            return new CommandResult(RESULT_MESSAGE_ERROR_DATE_INPUT);
        }

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

        if (StringUtil.isPresent(description)) {
            task.setDescription(description);
        }
        if (taskPriority != null) {
            task.setTaskPriority(taskPriority);
        }
        if (!tags.isEmpty()) {
            task.replaceTags(tags);
        }
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    /**
     * Checks whether the user input for dates is valid.
     * The input is valid if there is only one possible task type, or zero (task type is unchanged).
     * @param eventStartDateTime
     * @param eventEndDateTime
     * @param taskDeadline
     * @param isFloating
     * @return true if there is at most 1 possible task type
     */
    private boolean isValidTaskType(LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime,
            LocalDateTime taskDeadline, boolean isFloating) {
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
        return numberOfTaskTypes <= 1;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
