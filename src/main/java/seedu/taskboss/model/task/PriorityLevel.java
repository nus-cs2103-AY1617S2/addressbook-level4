package seedu.taskboss.model.task;


import seedu.taskboss.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority level in TaskBoss.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriorityLevel(String)}
 */
public class PriorityLevel {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority level should only contain"
            + " the words Yes or No (case-insensitive)";

    //@@author A0144904H
    public static final String PRIORITY_HIGH_VALUE = "High priority";
    public static final String PRIORITY_NO_VALUE = "No priority";
    public static final String PRIORITY_REGEX = "^(?:Yes|No|yes|no|y|n|Y|N)$";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority level string is invalid.
     */
    public PriorityLevel(String priorityLevel) throws IllegalValueException {
        assert priorityLevel != null;

        String trimmedPriorityLevel = priorityLevel.trim();

        if (priorityLevel.equals("")) {
            this.value = PRIORITY_NO_VALUE;
        } else {
            if (!isValidPriorityLevel(trimmedPriorityLevel)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            if (trimmedPriorityLevel.contains("y") || trimmedPriorityLevel.contains("Y")) {
                this.value = PRIORITY_HIGH_VALUE;
            } else {
                this.value = PRIORITY_NO_VALUE;
            }
        }
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriorityLevel(String test) {
        return test.matches(PRIORITY_REGEX) ||
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
