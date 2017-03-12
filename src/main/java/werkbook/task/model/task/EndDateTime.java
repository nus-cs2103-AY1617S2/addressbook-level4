package werkbook.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task End DateTime in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDateTime(String)}
 */
public class EndDateTime {

    public static final String MESSAGE_END_DATETIME_CONSTRAINTS = "End Date/Time must be in the format of "
            + "DD/MM/YYYY HHMM, where time is represented in 24 hours";
    public static final SimpleDateFormat END_DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");

    public final Date value;

    /**
     * Validates given start datetime.
     *
     * @throws IllegalValueException if given end date time string is invalid.
     */
    public EndDateTime(String endDateTime) throws IllegalValueException {
        assert endDateTime != null;
        String trimmedendDateTime = endDateTime.trim();
        try {
            this.value = END_DATETIME_FORMATTER.parse(trimmedendDateTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_END_DATETIME_CONSTRAINTS);
        }
    }

    /**
     * Returns if a given string is a valid end datetime.
     */
    public static boolean isValidEndDateTime(String test) {
        END_DATETIME_FORMATTER.setLenient(false);
        try {
            END_DATETIME_FORMATTER.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return END_DATETIME_FORMATTER.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                        && this.value.equals(((EndDateTime) other).value)); // state
                                                                            // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
