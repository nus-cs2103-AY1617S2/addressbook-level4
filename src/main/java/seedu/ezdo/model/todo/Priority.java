package seedu.ezdo.model.todo;


import seedu.ezdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Priority {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person Priority can take numerical values from 1 to 3, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[1-3]";

    public final int value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Priority(int priority) throws IllegalValueException {
        String stringPriority = Integer.toString(priority);
        assert stringPriority != null;
        if (!isValidAddress(stringPriority)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
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
        return Integer.toString(value).hashCode();
    }

}
