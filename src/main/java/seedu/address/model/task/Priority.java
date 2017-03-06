package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "task priority can only be 1, 2, or 3";
    //TODO: public static final String DATE_VALIDATION_REGEX = 

    public final String value;

    /**
     * Validity given priority.
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
     * Returns if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return true;
        //TODO: add REGEX
        //return test.matches(EMAIL_VALIDATION_REGEX);
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

}
