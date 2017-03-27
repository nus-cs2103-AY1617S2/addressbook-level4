package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority level in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriorityLevel(String)}
 */

public class PriorityLevel {

    public static final String MESSAGE_PRIORITY_LEVEL_CONSTRAINTS = "Priority Levels should be indicated by an integer,"
            + " ranging from 1 to 4; with 1 being the highest priority and 4 being the lowest priority.";

    public static final String PRIORITY_LEVEL_VALIDATION_REGEX = "[1-4]";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority level string is invalid.
     */

    public PriorityLevel(String priority) throws IllegalValueException {
        //assert priority != null;
        String trimmedPriority = priority.trim();
        //@@author A0139161J
        if (trimmedPriority.equals("")) {
            this.value = trimmedPriority;
        //@@author
        } else {
            if (!isValidPriorityLevel(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_LEVEL_CONSTRAINTS);
            }
            this.value = trimmedPriority;
        }
    }

    /**
     * Returns if a given string is a valid priority level.
     */
    public static boolean isValidPriorityLevel(String test) {
        return test.matches(PRIORITY_LEVEL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityLevel // instanceof handles nulls
                && this.value.equals(((PriorityLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
