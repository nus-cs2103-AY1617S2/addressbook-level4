package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's EndTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {
    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Event EndTime can take only 4 digits, and it should be in military time format"
            + " e.g. 0545 to represent 5.30am and 1430 to represent 2.30pm";

    /*
     * The first character of the EndTime must be 0,1, or 2 since it follows military format,
     * third character can only range from 0 to 5 to represent the number of minutes
     */
    public static final String ENDTIME_VALIDATION_REGEX = "[012][\\d][012345][\\d]";

    public final String value;

    /**
     * Validates given EndTime.
     *
     * @throws IllegalValueException if given EndTime string is invalid.
     */
    public EndTime(String endtime) throws IllegalValueException {
        assert endtime != null;
        if (!isValidEndTime(endtime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = endtime;
    }

    /**
     * Returns true if a given string is a valid event EndTime.
     */
    public static boolean isValidEndTime(String test) {
        return (test.matches(ENDTIME_VALIDATION_REGEX) && (Integer.parseInt(test) < 2400));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
