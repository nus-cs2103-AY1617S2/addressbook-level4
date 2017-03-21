package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0143873Y
/**
 * Represents a Task's time in the Tasklist.
 * Guarantees: immutable; is valid as declared in {@link #isValidClockTime(String)}
 */

public class ClockTime {

    public static final String MESSAGE_CLOCKTIME_CONSTRAINTS =
            "Time of task should be 2 alphanumeric/period strings separated by '@'";
    public static final String CLOCKTIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

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
