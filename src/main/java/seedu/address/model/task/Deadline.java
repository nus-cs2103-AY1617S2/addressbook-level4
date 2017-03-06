package seedu.address.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadline should strictly follow this format DD/MM/YYYY";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public final Date date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String dateString) throws IllegalValueException {
        assert dateString != null;
        String trimmedDateString = dateString.trim();
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            this.date = df.parse(trimmedDateString);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
                    && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
