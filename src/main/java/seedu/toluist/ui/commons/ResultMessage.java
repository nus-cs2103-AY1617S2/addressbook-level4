//@author A0127545A
package seedu.toluist.ui.commons;

import java.time.LocalDateTime;
import java.util.Set;

import com.google.common.base.Objects;

import seedu.toluist.commons.util.DateTimeFormatterUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.ui.UiStore;

/**
 * Represents the result message of a command execution.
 */
public class ResultMessage {
    private static final int INDEX_INVALID = -1;
    private static final String MESSAGE_RESULT_ADDED_TASK_HEADER_WITH_INDEX = "Added task at index %d:";
    private static final String MESSAGE_RESULT_ADDED_TASK_HEADER_WITHOUT_INDEX = "Added task in another tab";
    private static final String MESSAGE_RESULT_UPDATED_TASK_HEADER_WITH_INDEX = "Updated task at index %d:";
    private static final String MESSAGE_RESULT_UPDATED_TASK_HEADER_WITHOUT_INDEX = "Updated task in another tab";
    private static final String MESSAGE_RESULT_PARAMETER_INDENTATION = "- ";
    private static final String MESSAGE_RESULT_ADD_PARAMETER_FORMAT = ": \"%s\"";
    private static final String MESSAGE_RESULT_UPDATE_PARAMETER_FORMAT = ": \"%s\" to \"%s\"";
    private static final String STRING_NULL = "";
    private static final String STRING_NEW_LINE = "\n";
    private static final String STRING_SPACE = " ";
    private static final String TASK_TYPE = "Task type";
    private static final String TASK_DESCRIPTION = "Description";
    private static final String TASK_START_DATE = "Start date";
    private static final String TASK_END_DATE = "End date";
    private static final String TASK_PRIORITY = "Priority";
    private static final String TASK_REPEAT = "Repeat";
    private static final String TASK_REPEAT_UNTIL = "Repeat until";
    private static final String TASK_TAGS = "Tags";

    public static String getAddCommandResultMessage(Task newTask, UiStore uiStore) {
        int index = uiStore.getTaskIndex(newTask);
        String result = (index == INDEX_INVALID) ? MESSAGE_RESULT_ADDED_TASK_HEADER_WITHOUT_INDEX
                                   : String.format(MESSAGE_RESULT_ADDED_TASK_HEADER_WITH_INDEX, index + 1);
        result = appendAddedParameterMessage(result, TASK_TYPE, newTask.getTaskType());
        result = appendAddedParameterMessage(result, TASK_DESCRIPTION, newTask.getDescription());
        result = appendAddedParameterMessage(result, TASK_START_DATE, newTask.getStartDateTime());
        result = appendAddedParameterMessage(result, TASK_END_DATE, newTask.getEndDateTime());
        result = appendAddedParameterMessage(result, TASK_PRIORITY, newTask.getTaskPriority());
        result = appendAddedParameterMessage(result, TASK_REPEAT, newTask.getRecurringFrequency());
        result = appendAddedParameterMessage(result, TASK_REPEAT_UNTIL, newTask.getRecurringEndDateTime());
        result = appendAddedParameterMessage(result, TASK_TAGS, newTask.getAllTags());
        return result;
    }

    private static String appendAddedParameterMessage(String message, String category, Object newObject) {
        String result = message;
        if (newObject != null && StringUtil.isPresent(toStringCustomized(newObject))) {
            result = String.join(STRING_NEW_LINE, result,
                     String.format(MESSAGE_RESULT_PARAMETER_INDENTATION + category
                             + MESSAGE_RESULT_ADD_PARAMETER_FORMAT,
                     toStringCustomized(newObject)));
        }
        return result;
    }

    public static String getUpdateCommandResultMessage(Task oldTask, Task newTask, UiStore uiStore) {
        int index = uiStore.getTaskIndex(newTask);
        String result = (index == INDEX_INVALID) ? MESSAGE_RESULT_UPDATED_TASK_HEADER_WITHOUT_INDEX
                                   : String.format(MESSAGE_RESULT_UPDATED_TASK_HEADER_WITH_INDEX, index + 1);
        result = appendUpdatedParameterMessage(result, TASK_TYPE,
                oldTask.getTaskType(), newTask.getTaskType());
        result = appendUpdatedParameterMessage(result, TASK_DESCRIPTION,
                oldTask.getDescription(), newTask.getDescription());
        result = appendUpdatedParameterMessage(result, TASK_START_DATE,
                oldTask.getStartDateTime(), newTask.getStartDateTime());
        result = appendUpdatedParameterMessage(result, TASK_END_DATE,
                oldTask.getEndDateTime(), newTask.getEndDateTime());
        result = appendUpdatedParameterMessage(result, TASK_PRIORITY,
                oldTask.getTaskPriority(), newTask.getTaskPriority());
        result = appendUpdatedParameterMessage(result, TASK_REPEAT,
                oldTask.getRecurringFrequency(), newTask.getRecurringFrequency());
        result = appendUpdatedParameterMessage(result, TASK_REPEAT_UNTIL,
                oldTask.getRecurringEndDateTime(), newTask.getRecurringEndDateTime());
        result = appendUpdatedParameterMessage(result, TASK_TAGS,
                oldTask.getAllTags(), newTask.getAllTags());
        return result;
    }

    /**
     * Appends the message string when a parameter gets updated
     * @param message
     * @param category
     * @param oldObject
     * @param newObject
     * @return the updated message string
     */
    private static String appendUpdatedParameterMessage(String message, String category,
            Object oldObject, Object newObject) {
        String result = message;
        if (!Objects.equal(oldObject, newObject)) {
            result = String.join(STRING_NEW_LINE, result,
                     String.format(MESSAGE_RESULT_PARAMETER_INDENTATION + category
                             + MESSAGE_RESULT_UPDATE_PARAMETER_FORMAT,
                     toStringCustomized(oldObject), toStringCustomized(newObject)));
        }
        return result;
    }

    /**
     * Helper method to convert certain types of objects to string.
     * The types handled here is unique to only Task model, though.
     * @param object
     * @return string representing the object in human-readable form
     */
    private static String toStringCustomized(Object object) {
        if (object == null) {
            return STRING_NULL;
        } else if (object instanceof LocalDateTime) {
            return String.join(STRING_SPACE, DateTimeFormatterUtil.formatDate((LocalDateTime) object),
                    DateTimeFormatterUtil.formatTime((LocalDateTime) object));
        } else if (object instanceof Set<?>) {
            String[] tagNames = ((Set<?>) object)
                    .stream()
                    .map(t -> ((Tag) t).getTagName())
                    .toArray(size -> new String[size]);
            String result = String.join(STRING_SPACE, tagNames);
            return result;
        } else {
            return object.toString();
        }
    }
}
