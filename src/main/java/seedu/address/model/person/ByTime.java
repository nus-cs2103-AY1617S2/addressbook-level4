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

    /*
     * must be in digits only
     *
     */
    public static final String BYTIME_VALIDATION_REGEX = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";

    public final LocalTime value;
//@@author A0110491U A0121668A
    /**
     * Validates given ByTime.
     *
     * @throws IllegalValueException if given ByTime string is invalid.
     */
    public ByTime(String byTime) throws IllegalValueException {
        if (byTime == null) {
            this.value = null;
        } else {
            if (!isValidByTime(byTime)) {
                throw new IllegalValueException(MESSAGE_BYTIME_CONSTRAINTS);
            }
            this.value = StringUtil.parseStringToTime(byTime);
        }
    }
//@@author A0121668A
    /**
     * Returns true if a given string is a valid deadline ByTime.
     */
    public static boolean isValidByTime(String test) {
        if (test == null) {
            return true;
        }
        return test.matches(BYTIME_VALIDATION_REGEX);
    }
//@@author
    @Override
    public String toString() {
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

}
