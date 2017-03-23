package seedu.address.model.person;

import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

/**
 * Represents an Event's StartTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Event start time should be in HH:MM format e.g. 00:00 represents midnight";

    public final LocalTime value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTimeArg) throws IllegalValueException {
        if (startTimeArg == null) {
            this.value = null;
        } else {
        	try {
        		this.value = StringUtil.parseStringToTime(startTimeArg);
        	} catch (IllegalValueException illegalValueException) {
        		throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        	}
        }
    }

    @Override
    public String toString() {
        return value.toString();
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
