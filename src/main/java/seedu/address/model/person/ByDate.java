package seedu.address.model.person;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

//@@author A0121668A
/**
 * Represents a Deadline's ByDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidByDate(String)}
 */
public class ByDate {

    public static final String MESSAGE_BYDATE_CONSTRAINTS =
            "Deadline ByDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    public final LocalDate value;

    public LocalDate getValue() {
        return value;
    }

//@@author A0110491U A0121668A
    /**
     * Validates given by date.
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public ByDate(String byDateArg) throws IllegalValueException {
        if (byDateArg == null) {
            this.value = null;
        } else {
            try {
                this.value = StringUtil.parseStringToDate(byDateArg);
            } catch (IllegalValueException illegalValueException) {
                throw new IllegalValueException(MESSAGE_BYDATE_CONSTRAINTS);
            }
        }
    }
    
//@@author A0121668A
    /*
     * For JAXB use
     */
    public ByDate(LocalDate bydate) {
        value = bydate;
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

}
