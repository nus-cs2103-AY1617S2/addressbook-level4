package seedu.whatsleft.model.activity;

import java.time.DateTimeException;
import java.time.LocalTime;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.StringUtil;

//@@author A0148038A
/**
 * Represents an Event's EndTime in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Event end time can only be in this format: hhmm, e.g. 1300";

    public final LocalTime value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public EndTime(String endTimeArg) throws IllegalValueException {
        if (endTimeArg == null) {
            this.value = null;
        } else {
            try {
                this.value = StringUtil.parseStringToTime(endTimeArg);
            } catch (DateTimeException illegalValueException) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
        }
    }

    //@@author A0110491U
    /**
     * Checks if given EndTime string is valid
     * returns true if it is valid according to MESSAGE_ENDTIME_CONSTRAINTS
     */
    public static boolean isValidEndTime(String args) {
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
    public EndTime(LocalTime endTime) {
        value = endTime;
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
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(EndTime o) {
        return this.getValue().compareTo(o.getValue());
    }
}
