package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Person date's format should resemble a date";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
        
        this.value = parse(trimmedDate);
    }

    private String parse(String rawDate) throws IllegalValueException {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(rawDate);
        String dateText = groups.get(0).getDates().get(0).toString();
        return dateText;
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