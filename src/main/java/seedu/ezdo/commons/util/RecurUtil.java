package seedu.ezdo.commons.util;

import seedu.ezdo.model.todo.ReadOnlyTask;

//@@author A0139177W
/**
 * Utility method for Recur
 */
public class RecurUtil {

    /**
     * Checks if a recurring status is valid with at least a start date and/or
     * due date present in a task. Floating tasks should not have a recurring
     * status.
     * 
     * @param task
     * @return false if the task is a floating task
     * @return true otherwise
     */
    public static boolean isRecurValid(ReadOnlyTask task) {
        assert task != null;
        String taskStartDate = task.getStartDate().toString();
        String taskDueDate = task.getDueDate().toString();
        final boolean isStartDateMissing = taskStartDate.isEmpty();
        final boolean isDueDateMissing = taskDueDate.isEmpty();
        if (isStartDateMissing && isDueDateMissing) {
            return false;
        } else {
            return true;
        }
    }

}
