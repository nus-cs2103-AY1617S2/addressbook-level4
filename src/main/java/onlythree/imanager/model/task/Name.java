package onlythree.imanager.model.task;

import onlythree.imanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    //@@author A0140023E
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task names should not contain forward slashes, and it should not be blank";

    /**
     * The first character of the name must not be a whitespace or a forward slash,
     * otherwise a whitespace character (i.e.0x20\t\n\x0B\f\r) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[^/\\s][^/]*";

    //@@author
    public final String value;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.value = trimmedName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.value.equals(((Name) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
