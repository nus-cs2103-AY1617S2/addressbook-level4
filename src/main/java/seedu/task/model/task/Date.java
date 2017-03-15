package seedu.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in KIT.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be in this format: DD-MM-YYYY";
    public static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {
        format.setLenient(false);
        try {
            format.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
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
