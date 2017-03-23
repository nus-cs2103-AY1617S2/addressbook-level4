package seedu.address.model.person;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;


/**
 * Represents an Event's EndDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

	public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Event start date should be in YYYY-MM-DD format e.g. 2010-12-05";

    public final LocalDate value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public EndDate(String endDateArg) throws IllegalValueException {
    	if (endDateArg == null) {
            this.value = null;
        } else {
        	try {
        		this.value = StringUtil.parseStringToDate(endDateArg);
        	} catch (IllegalValueException illegalValueException) {
        		throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
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
                || (other instanceof EndDate // instanceof handles nulls
                && this.value.equals(((EndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
