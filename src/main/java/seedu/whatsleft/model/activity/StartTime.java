package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalTime;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents an Event's StartTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Event start time can only be in this format: hhmm, e.g. 1300";

    public final LocalTime value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTimeArg) throws IllegalValueException {
        if (startTimeArg == null) {
            this.value = null;
        } else {
            try {
                this.value = StringUtil.parseStringToTime(startTimeArg);
            } catch (DateTimeException dateTimeException) {
                throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
            }
        }
    }

    //@@author A0110491U
    /**
     * Checks if given StartTime string is valid
     * returns true if it is valid according to MESSAGE_STARTTIME_CONSTRAINTS
     */
    public static boolean isValidStartTime(String args) {
        try {
            StringUtil.parseStringToTime(args);
        } catch (DateTimeException ive) {
            return false;
        }
        return true;
    }
    //@@author

    /*
     * For JAXB use
     */
    public StartTime(LocalTime startTime) {
        value = startTime;
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
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compare with another StartTime object
     *
     * @param a StartTime object
     * @return -1 if this StartTime object is earlier than the given StartTime object
     */
    public int compareTo(StartTime o) {
        return this.getValue().compareTo(o.getValue());
    }
}
