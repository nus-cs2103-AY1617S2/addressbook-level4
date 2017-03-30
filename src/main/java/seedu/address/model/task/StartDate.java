package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.List;

import com.wanasit.chrono.Chrono;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class StartDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "A task StartDate's format should be mm.dd";
    //set date to mm.dd(e.g.01.01)
    public static final String DATE_VALIDATION_REGEX = "\\d\\d\\.\\d\\d";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public StartDate(String sdate) throws IllegalValueException {
        assert sdate != null;
        String trimmedDate = sdate.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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

}
