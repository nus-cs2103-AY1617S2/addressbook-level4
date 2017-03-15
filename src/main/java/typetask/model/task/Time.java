package typetask.model.task;

import typetask.commons.exceptions.IllegalValueException;

public class Time {
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should be in hh:mm am/pm format. hh range from 1 to 12 and mm range from 00 to 59.";
    public static final String TIME_VALIDATION_REGEX = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";

    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        String trimmedTime = time.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        if (test.equals("")) {
            return true;
        } else {
            return test.matches(TIME_VALIDATION_REGEX);
        }

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                        && this.value.equals(((Time) other).value)); // state check
    }
}
