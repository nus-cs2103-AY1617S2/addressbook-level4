package seedu.address.model.task;

import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

/**
 * Represents a Date in the YTomorrow.
 */
public abstract class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Person date's format should resemble a date";
    
    private static final String BAD_DATE = "nil";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;

        if (date == null) {
            this.value = BAD_DATE;
        } else {
            this.value = DateUtil.parse(date).toString();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
