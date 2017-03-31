//@@author A0124153U


package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start in the ToDoApp
 * must contain a date, starting time is optional
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Task start";

    /*
     * The first character of the start must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DEADLINE_VALIDATION_REGEX = "([^\\s])+";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start string is invalid.
     */
    public Start(String start) throws IllegalValueException {
        assert start != null;
        if (!isValidStart(start)) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.value = start;
    }

    /**
     * Returns true if a given string is a valid start.
     */
    public static boolean isValidStart(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
