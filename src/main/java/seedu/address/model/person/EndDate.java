package seedu.address.model.person;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents an Event's EndDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Event End Date can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    public final LocalDate value;

    /**
     * Validates given end date.
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public EndDate(String endDateArg) throws IllegalValueException {
        if (endDateArg == null) {
            this.value = null;
        } else {
            try {
                this.value = StringUtil.parseStringToDate(endDateArg);
            } catch (DateTimeException illegalValueException) {
                throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
            }
        }
    }

    //@@author A0110491U
    /**
     * Checks if given EndDate string is valid
     * returns true if it is valid according to MESSAGE_ENDDATE_CONSTRAINTS
     */
    public static boolean isValidEndDate(String args) {
        try {
            StringUtil.parseStringToDate(args);
        } catch (DateTimeException ive) {
            return false;
        }
        return true;
    }
    //@@author

    /*
     * for JAXB use
     */
    public EndDate(LocalDate startDate) {
        value = startDate;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return null;
        }
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

    public int compareTo(EndDate o) {
        return this.getValue().compareTo(o.getValue());
    }

}
