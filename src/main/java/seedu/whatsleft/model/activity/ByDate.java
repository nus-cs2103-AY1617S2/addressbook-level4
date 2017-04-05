package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.DateTimeNLUtil;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0121668A
/**
 * Represents a Deadline's ByDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByDate(String)}
 */
public class ByDate {

    public static final String MESSAGE_BYDATE_CONSTRAINTS =
            "Deadline ByDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    public final LocalDate value;
    public final boolean isExisting;

    public LocalDate getValue() {
        return value;
    }

    //@@author A0110491U
    /**
     * Validates given by date. If given in digits, tries to parse into date. If given in non-digit
     * format, tries to process using the DateTimeNLUtil class that tries to read natural language
     *
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

    //@@author A0110491U
    /**
     * Checks if given ByDate string is valid
     * returns true if it is valid according to MESSAGE_BYDATE_CONSTRAINTS
     */
    public static boolean isValidByDate(String args) {
        try {
            StringUtil.parseStringToDate(args);
        } catch (DateTimeException ive) {
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
        }
        return value.toString();
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
    public boolean isExisting() {
        return this.isExisting;
    }

    public int compareTo(ByDate o) {
        return this.getValue().compareTo(o.getValue());
    }

}
