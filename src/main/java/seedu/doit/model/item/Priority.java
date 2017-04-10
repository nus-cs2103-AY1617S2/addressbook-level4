// @@author A0139399J
package seedu.doit.model.item;

import seedu.doit.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should only be low med high";
    public static final String PRIORITY_VALIDATION_REGEX = "(low)|(med)|(high)";
    public static final String PRIORITY_LOW = "low";
    public static final String PRIORITY_MED = "med";

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Priority // instanceof handles nulls
            && this.value.equals(((Priority) other).value)); // state check
    }

    private int getPriorityValue() {
        if (PRIORITY_LOW.equals(value)) {
            return 3;
        } else if (PRIORITY_MED.equals(value)) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public int compareTo(Priority other) {
        Integer currPriorityValue = getPriorityValue();
        Integer otherPriorityValue = other.getPriorityValue();
        return currPriorityValue.compareTo(otherPriorityValue);
    }

}
