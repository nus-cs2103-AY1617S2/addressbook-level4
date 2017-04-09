package seedu.address.model.task;

import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Start Date in the YTomorrow.
 */
//@@author A0164032U
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Person start date's format should resemble a date";
    private static final String BAD_DATE = "nil";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public StartDate(String sdate) throws IllegalValueException {
        assert sdate != null;
        
        if (sdate == null) {
            this.value = BAD_DATE;
        } else {
            this.value = parse(sdate);
        }   
    }

    private String parse(String rawDate) throws IllegalValueException {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawDate);
            String dateText = groups.get(0).getDates().get(0).toString();
            return dateText;
        } catch (RuntimeException e) {
            throw new IllegalValueException(e.toString());
        }
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
