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
            "Event End Date can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

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

    //@@author A0121668A
    /*
     * for JAXB use
     */
    public StartDate(LocalDate startdate) {
        value = startdate;
    }
   
    public LocalDate getValue() {
        return value;
    }
//@@author

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
