//@@author A0124153U


package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the ToDoApp
 * stores as an integer, the higher the value, the higher the priority
 * Guarantees: integer
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Task priority";

    /*
     * priority input must be an integer
     */
    public static final String PRIORITY_VALIDATION_REGEX = "(\\d*)";

    public final int value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority is invalid.
     */
    public Priority(int priority) throws IllegalValueException {
        assert priority >= 0;
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(int test) {
        if (test == (int) test) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value == ((Priority) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

}
