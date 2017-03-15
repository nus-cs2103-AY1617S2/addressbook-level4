//@@author A0124153U


package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's notes in the ToDoApp
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Notes {

    public static final String MESSAGE_NOTES_CONSTRAINTS =
            "Task notes";

    /*
     * The first character of the notes must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOTES_VALIDATION_REGEX = "([^\\s])+";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Notes(String notes) throws IllegalValueException {
        assert notes != null;
        if (!isValidDeadline(notes)) {
            throw new IllegalValueException(MESSAGE_NOTES_CONSTRAINTS);
        }
        this.value = notes;
    }

    /**
     * Returns true if a given string is a valid person deadline.
     */
    public static boolean isValidDeadline(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
