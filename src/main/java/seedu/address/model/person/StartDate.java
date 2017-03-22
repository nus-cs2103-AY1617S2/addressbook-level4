package seedu.address.model.person;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;


/**
 * Represents an event's start date in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {
	
	public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Event start date should be in YYYY-MM-DD format e.g. 2010-12-05";

    public final LocalDate value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public StartDate(String startDateArg) throws IllegalValueException {
        try {
        	this.value = StringUtil.parseStringToDate(startDateArg);
        } catch (IllegalValueException illegalValueException) {
        	throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
