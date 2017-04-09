package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.DateTimeNLUtil;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents an event's start date in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Event Start Date can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)\n"
            + "today, tmr, mon, tue, wed, thur, fri, sat, sun, next, following are also acceptable\n"
            + "Example: next fri\n"
            + "Example: 250317\n";

    public final LocalDate value;
    //@@author A0110491U
    /**
     * Validates given start date. If given in digits, tries to parse into date. If given in non-digit
     * format, tries to process using the DateTimeNLUtil class that tries to read natural language
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public StartDate(String startDateArg) throws IllegalValueException {
        if (startDateArg.matches("\\d+")) {
            try {
                this.value = StringUtil.parseStringToDate(startDateArg);
            } catch (DateTimeException illegalValueException) {
                throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
            }
        } else {
            DateTimeNLUtil dt = new DateTimeNLUtil();
            String nldate = dt.getDate(startDateArg);
            this.value = StringUtil.parseStringToDate(nldate);
        }
    }

  //@@author A0110491U
    /**
     * Checks if given StartDate string is valid
     * returns true if it is valid according to MESSAGE_STARTDATE_CONSTRAINTS
     */
    public static boolean isValidStartDate(String args) {
        try {
            StringUtil.parseStringToDate(args);
        } catch (DateTimeException ive) {
            return false;
        }
        return true;
    }
    //@@author

    //@@author A0121668A
    /*
     * for JAXB use
     */
    public StartDate(LocalDate startDate) {
        value = startDate;
    }

    public LocalDate getValue() {
        return value;
    }

    //@@author A0148038A
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

    /**
     * Compare with another StartDate object
     *
     * @param a StartDate object
     * @return -1 if this StartDate object is earlier than the given StartDate object
     */
    public int compareTo(StartDate o) {
        return this.getValue().compareTo(o.getValue());
    }

}
