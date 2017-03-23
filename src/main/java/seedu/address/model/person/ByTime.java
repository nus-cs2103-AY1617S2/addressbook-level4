package seedu.address.model.person;

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
            "Deadline ByTime can only be in this format: HH:MM(AM/PM) format, e.g. 12:00pm";

    public final LocalTime value;
//@@author A0110491U A0121668A
    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public ByTime(String byTimeArg) throws IllegalValueException {
        if (byTimeArg == null) {
            this.value = null;
        } 
        else {
            try {
                this.value = StringUtil.parseStringToTime(byTimeArg);
            } catch (IllegalValueException illegalValueException) {
                throw new IllegalValueException(MESSAGE_BYTIME_CONSTRAINTS);
                }
            }
        }
//@@author A0121668A
/*
 * For JAXB use
 */
    public ByTime(LocalTime bytime) {
            value = bytime;
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
                || (other instanceof ByTime // instanceof handles nulls
                && this.value.equals(((ByTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public LocalTime getValue() {
        return value;
    }

}
