package seedu.address.model.person;

import java.time.DateTimeException;
import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

//@@author A0121668A
/**
 * Represents a Deadline's ByTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByTime(String)}
 */
public class ByTime {

    public static final String MESSAGE_BYTIME_CONSTRAINTS =
            "Deadline ByTime can only be in this format: hhmm, e.g. 1300";

    public final LocalTime value;
    public final boolean isExisting;

    //@@author A0121668A
    /**
     * Validates given start time.
     *
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
        } catch (DateTimeException ive) {
            return false;
        }
        return true;
    }
    //@@author

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
        }
        return value.toString();
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
    public boolean isExisting() {
        return this.isExisting;
    }
    public int compareTo(ByTime o) {
        return this.getValue().compareTo(o.getValue());
    }
}
