package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.DateTimeNLUtil;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents a taks's due date in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByDate(String)}
 */
public class ByDate {

    public static final String MESSAGE_BYDATE_CONSTRAINTS =
            "Task ByDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)\n"
            + "today, tmr, mon, tue, wed, thur, fri, sat, sun, next, following are also acceptable\n"
            + "Example: next fri\n"
            + "Example: 250317\n";

    public final LocalDate value;
    public final boolean isExisting; // to check whether the task has a by date

    public LocalDate getValue() {
        return value;
    }

    //@@author A0148038A
    /**
     * Validates given by date. If given in digits, tries to parse into date. If given in non-digit
     * format, tries to process using the DateTimeNLUtil class that tries to read natural language
     *
     * @param a byDateArg in string format
     * @throws IllegalValueException if given start date is invalid.
     */
    public ByDate(String byDateArg) throws IllegalValueException {
        if (byDateArg == null) {
            this.value = null;
            this.isExisting = false;
        } else {
            if (byDateArg.matches("\\d+")) {
                try {
                    this.value = StringUtil.parseStringToDate(byDateArg);
                    this.isExisting = true;
                } catch (DateTimeException illegalValueException) {
                    throw new IllegalValueException(MESSAGE_BYDATE_CONSTRAINTS);
                }
            } else {
                DateTimeNLUtil dt = new DateTimeNLUtil();
                String nldate = dt.getDate(byDateArg);
                this.value = StringUtil.parseStringToDate(nldate);
                this.isExisting = true;
            }
        }
    }

    //@@author A0148038A
    /**
     * Checks if given ByDate string is valid
     *
     * @param a by date string to be checked
     * @returns a boolean variable, true if it is valid, false otherwise
     */
    public static boolean isValidByDate(String args) {
        try {
            StringUtil.parseStringToDate(args);
        } catch (DateTimeException dte) {
            return false;
        }
        return true;
    }

    //@@author A0121668A
    /*
     * For JAXB use
     */
    public ByDate(LocalDate bydate) {
        value = bydate;
        if (bydate == null) {
            isExisting = false;
        } else {
            isExisting = true;
        }
    }

    //@@author
    @Override
    public String toString() {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ByDate // instanceof handles nulls
                && this.value.equals(((ByDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    //@@author A0148038A
    /**
     * Check whether a by date is present
     *
     * @return a boolean variable, true if by date is present, false otherwise
     */
    public boolean isExisting() {
        return this.isExisting;
    }

    /**
     * Compare with another ByDate object
     *
     * @param a ByDate object
     * @return -1 if this ByDate object is earlier than the given ByDate object
     */
    public int compareTo(ByDate o) {
        return this.getValue().compareTo(o.getValue());
    }

}
