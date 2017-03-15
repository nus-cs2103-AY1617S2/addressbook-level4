package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority>{

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be between 1-3";

    public final String value;

    /**
     * Validates given priority number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority != null) {
            String trimmedPriority = priority.trim();
            if (!isValidPriority(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.value = trimmedPriority;
        } else {
            this.value = null;
        }
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        return (test.equals("1") || test.equals("2") || test.equals("3"));
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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Priority comparePriority) {
        int thisValue = Integer.parseInt(this.value);
        int otherValue = Integer.parseInt(comparePriority.value);
        return thisValue - otherValue;
    }

}
