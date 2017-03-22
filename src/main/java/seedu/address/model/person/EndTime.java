package seedu.address.model.person;

import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

/**
 * Represents an Event's EndTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {
	public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Event start time should be in HH:MM format e.g. 00:00 represents midnight";

    public final LocalTime value;
    
    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public EndTime(String endTimeArg) throws IllegalValueException {
        if (endTimeArg == null) {
            this.value = null;
        } else {
        	try {
        		this.value = StringUtil.parseStringToTime(endTimeArg);
        	} catch (IllegalValueException illegalValueException) {
        		throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
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
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
