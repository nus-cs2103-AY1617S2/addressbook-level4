package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.DateTimeNLUtil;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents an Event's EndDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Event End Date can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)\n"
            + "today, tmr, mon, tue, wed, thur, fri, sat, sun, next, following are also accecptable\n"
            + "Example: next fri\n"
            + "Example: 250317\n";

    public final LocalDate value;
    //@@author A0110491U
    /**
     * Validates given end date. If given in digits, tries to parse into date. If given in non-digit
     * format, tries to process using the DateTimeNLUtil class that tries to read natural language
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public EndDate(String endDateArg) throws IllegalValueException {
        if (endDateArg == null) {
            this.value = null;
        } else {
            if (endDateArg.matches("\\d+")) {
                try {
                    this.value = StringUtil.parseStringToDate(endDateArg);
                } catch (DateTimeException illegalValueException) {
                    throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
                }
            } else {
                DateTimeNLUtil dt = new DateTimeNLUtil();
                String nldate = dt.getDate(endDateArg);
                this.value = StringUtil.parseStringToDate(nldate);
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

    //@@author A0148038A
    /**
     * Compare with another EndDate object
     *
     * @param a EndDate object
     * @return -1 if this EndDate object is earlier than the given EndDate object
     */
    public int compareTo(EndDate o) {
        return this.getValue().compareTo(o.getValue());
    }

}
