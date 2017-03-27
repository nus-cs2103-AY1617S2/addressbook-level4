//@@author A0127545A
package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Objects;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeFormatterUtil;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_ERROR_DATE_INPUT =
            "Something is wrong with the given dates input";
    private static final String RESULT_MESSAGE_ERROR_RECURRING_AND_STOP_RECURRING =
            "Input contains both recurring and stop recurring arguments at the same time.";
    private static final String RESULT_MESSAGE_ERROR_FLOATING_AND_NON_FLOATING =
            "Input contains both floating and non-floating task arguments at the same time.";
    private static final String RESULT_MESSAGE_ERROR_CLONING_ERROR = "Bad things happened when cloning task.";

    private static final Logger logger = LogsCenter.getLogger(UpdateTaskController.class);

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        CommandResult commandResult;

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.PARAMETER_TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());
        Task task = uiStore.getShownTasks(indexes).get(0);

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

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        uiStore.setCommandResult(commandResult);
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, true);
    }

    private CommandResult update(Task task, String description,
            LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime, LocalDateTime taskDeadline,
            boolean isFloating, String taskPriority, Set<Tag> tags,
            String recurringFrequency, LocalDateTime recurringUntilEndDate, boolean isStopRecurring) {
        if (!isValidTaskType(eventStartDateTime, eventEndDateTime, taskDeadline, isFloating)) {
            return new CommandResult(RESULT_MESSAGE_ERROR_DATE_INPUT);
        }
        if (isStopRecurring && (StringUtil.isPresent(recurringFrequency) || recurringUntilEndDate != null)) {
            return new CommandResult(RESULT_MESSAGE_ERROR_RECURRING_AND_STOP_RECURRING);
        }
        if (isFloating && (eventStartDateTime != null || eventEndDateTime != null || taskDeadline != null)) {
            return new CommandResult(RESULT_MESSAGE_ERROR_FLOATING_AND_NON_FLOATING);
        }
        try {
            Task taskCopy = (Task) task.clone();
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
            if (StringUtil.isPresent(taskPriority)) {
                task.setTaskPriority(taskPriority);
            }
            if (StringUtil.isPresent(recurringFrequency)) {
                task.setRecurringFrequency(recurringFrequency);
            }
            if (recurringUntilEndDate != null) {
                task.setRecurringEndDateTime(recurringUntilEndDate);
            }
            if (!tags.isEmpty()) {
                task.replaceTags(tags);
            }
            if (isStopRecurring) {
                task.unsetRecurring();
            }
            return new CommandResult(getSuccessUpdateMessage(taskCopy, task));
        } catch (IllegalArgumentException illegalArgumentException) {
            return new CommandResult(illegalArgumentException.getMessage());
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(RESULT_MESSAGE_ERROR_CLONING_ERROR);
        }
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

    private String getSuccessUpdateMessage(Task oldTask, Task newTask) {
        int index = TodoList.getInstance().find(newTask);
        String result = String.format("Updated the following details at index %d:", index);
        result = addMoreSuccessMessage(result, "Task type", oldTask.getTaskType(), newTask.getTaskType());
        result = addMoreSuccessMessage(result, "Description", oldTask.getDescription(), newTask.getDescription());
        result = addMoreSuccessMessage(result, "Start date", oldTask.getStartDateTime(), newTask.getStartDateTime());
        result = addMoreSuccessMessage(result, "End date", oldTask.getEndDateTime(), newTask.getEndDateTime());
        result = addMoreSuccessMessage(result, "Priority", oldTask.getTaskPriority(), newTask.getTaskPriority());
        result = addMoreSuccessMessage(result, "Repeat",
                oldTask.getRecurringFrequency(), newTask.getRecurringFrequency());
        result = addMoreSuccessMessage(result, "Repeat until",
                oldTask.getRecurringEndDateTime(), newTask.getRecurringEndDateTime());
        result = addMoreSuccessMessage(result, "Tags", oldTask.getAllTags(), newTask.getAllTags());
        return result;
    }

    private String addMoreSuccessMessage(String result, String category, Object oldObject, Object newObject) {
        if (!Objects.equal(oldObject, newObject)) {
            result = String.join("\n", result, String.format("- " + category + ": \"%s\" to \"%s\"",
                    toStringCustomized(oldObject), toStringCustomized(newObject)));
        }
        return result;
    }

    /**
     * Helper method to convert certain types of objects to string.
     * @param object
     * @return string representing the object in human-readable form
     */
    private String toStringCustomized(Object object) {
        if (object == null) {
            return "null";
        } else if (object instanceof LocalDateTime) {
            return String.join(" ", DateTimeFormatterUtil.formatDate((LocalDateTime) object),
                    DateTimeFormatterUtil.formatTime((LocalDateTime) object));
        } else if (object instanceof Set<?>) {
            String result = "[";
            for (Object oneObject : (Set<?>) object) {
                if (oneObject instanceof Tag) {
                    result = String.join(" ", result, ((Tag) oneObject).getTagName());
                }
            }
            result = String.join(" ", result, "]");
            return result;
        } else {
            return object.toString();
        }
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
