package seedu.bulletjournal.model.task;


import seedu.bulletjournal.commons.exceptions.IllegalValueException;

/**
 * Address morphed into task start time
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class BeginDate {

    public static final String MESSAGE_BEGINDATE_CONSTRAINTS =
            "Task begin date can take any values, and it should not be blank";

    /*
     * The first character of the begin date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BEGINDATE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public BeginDate(String address) throws IllegalValueException {
        assert address != null;
        if (!isValidBeginDate(address)) {
            throw new IllegalValueException(MESSAGE_BEGINDATE_CONSTRAINTS);
        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid task email.
     */
    public static boolean isValidBeginDate(String test) {
        return test.matches(BEGINDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BeginDate // instanceof handles nulls
                && this.value.equals(((BeginDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
