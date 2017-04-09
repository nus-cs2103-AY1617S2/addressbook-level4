package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalTime;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents a task's due time in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByTime(String)}
 */
public class ByTime {

    public static final String MESSAGE_BYTIME_CONSTRAINTS =
            "Deadline ByTime can only be in this format: hhmm, e.g. 1300";

    public final LocalTime value;
    public final boolean isExisting; // to check whether the task has a by time

    /**
     * Validates given start time.
     *
     * @param byTime Argument in string format
     * @throws IllegalValueException if given start time is invalid.
     */
    public ByTime(String byTimeArg) throws IllegalValueException {
        if (byTimeArg == null) {
            this.value = null;
            this.isExisting = false;
        } else {
            try {
                this.value = StringUtil.parseStringToTime(byTimeArg);
                this.isExisting = true;
            } catch (DateTimeException illegalValueException) {
                throw new IllegalValueException(MESSAGE_BYTIME_CONSTRAINTS);
            }
        }
    }

    //@@author A0110491U
    /**
     * Checks if given ByTime string is valid
     * returns true if it is valid according to MESSAGE_BYTIME_CONSTRAINTS
     */
    public static boolean isValidByTime(String args) {
        try {
            StringUtil.parseStringToTime(args);
        } catch (DateTimeException dte) {
            return false;
        }
        return true;
    }

    //@@author A0121668A
    /**
     * For JAXB use
     */
    public ByTime(LocalTime bytime) {
        value = bytime;
        if (bytime == null) {
            isExisting = false;
        } else {
            isExisting = true;
        }
    }

    public LocalTime getValue() {
        return value;
    }

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
                || (other instanceof ByTime // instanceof handles nulls
                && this.value.equals(((ByTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    //@@author A0148038A
    /**
     * check whether a by time is present
     * @return a boolean variable, true if by time is present, false otherwise
     */
    public boolean isExisting() {
        return this.isExisting;
    }

    /**
     * Compare with another ByTime object
     *
     * @param a ByTime object
     * @return -1 if this ByTime object is earlier than the given ByTime object
     */
    public int compareTo(ByTime o) {
        return this.getValue().compareTo(o.getValue());
    }
}
