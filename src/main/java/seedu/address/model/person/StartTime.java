package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's StartTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Event StartTime can take only 4 digits, and it should be in military time format"
            + " e.g. 0545 to represent 5.30am and 1430 to represent 2.30pm";

    /*
     * The first character of the StartTime must be 0,1 or 2 since it follows military format,
     * third character can only range from 0 to 5 to represent the number of minutes
     */
    public static final String STARTTIME_VALIDATION_REGEX = "[012][\\d][012345][\\d]";

    public final String value;

    /**
     * Validates given StartTime.
     *
     * @throws IllegalValueException if given StartTime string is invalid.
     */
    public StartTime(String starttime) throws IllegalValueException {
        if (starttime == null) {
            this.value = null;
        } else {
            if (!isValidStartTime(starttime)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
            }
            this.value = starttime;
        }
    }

    /**
     * Returns true if a given string is a valid event StartTime.
     */
    public static boolean isValidStartTime(String test) {
        if (test == null) {
            return true;
        }
        return (test.matches(STARTTIME_VALIDATION_REGEX) && (Integer.parseInt(test) < 2400));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
