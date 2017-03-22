package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0143873Y
/**
 * Represents a Task's time in the Tasklist.
 * Guarantees: immutable; is valid as declared in {@link #isValidClockTime(String)}
 */

public class ClockTime {
    //private final Logger logger = LogsCenter.getLogger(ClockTime.class);

    public static final String MESSAGE_CLOCKTIME_CONSTRAINTS =
            "Clock time of task should be 24-hour form, eg: 23:59";
    public static final String CLOCKTIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public final String value;

    /**
     * Validates given clockTime.
     *
     * @throws IllegalValueException if given clockTime address string is invalid.
     */
    public ClockTime(String clockTime) throws IllegalValueException {
        assert (clockTime != null);
        String trimmedClockTime = clockTime.trim();
        if (!isValidClockTime(trimmedClockTime)) {
            throw new IllegalValueException(MESSAGE_CLOCKTIME_CONSTRAINTS);
        }
        this.value = trimmedClockTime;
    }

    /**
     * Returns if a given string is a valid task clockTime.
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
