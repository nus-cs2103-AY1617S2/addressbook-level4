package seedu.taskboss.model.task;

import seedu.taskboss.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority level in TaskBoss.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriorityLevel(String)}
 */
public class PriorityLevel {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority level should only contain"
            + " numbers 1, 2 or 3, or it can be blank.";
    public static final String PRIORITY_VALIDATION_REGEX = "[1-3]";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority level string is invalid.
     */
    public PriorityLevel(String priorityLevel) throws IllegalValueException {
        assert priorityLevel != null;
        String trimmedPriorityLevel = priorityLevel.trim();
        if (!isValidPriorityLevel(trimmedPriorityLevel)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriorityLevel;
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriorityLevel(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX) ||
                "".equals(test);
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
