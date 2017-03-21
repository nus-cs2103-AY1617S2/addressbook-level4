package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClockTime(String)}
 */
public class ClockTime {

    public static final String MESSAGE_CLOCKTIME_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String CLOCKTIME_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public ClockTime(String email) throws IllegalValueException {
        assert email != null;
        String trimmedClockTime = email.trim();
        if (!isValidClockTime(trimmedClockTime)) {
            throw new IllegalValueException(MESSAGE_CLOCKTIME_CONSTRAINTS);
        }
        this.value = trimmedClockTime;
    }

    /**
     * Returns if a given string is a valid task email.
     */
    public static boolean isValidClockTime(String test) {
        return test.matches(CLOCKTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockTime // instanceof handles nulls
                && this.value.equals(((ClockTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
