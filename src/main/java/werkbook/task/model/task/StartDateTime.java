package werkbook.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task Start DateTime in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDateTime(String)}
 */
public class StartDateTime {

    public static final String MESSAGE_START_DATETIME_CONSTRAINTS = "Start Date/Time must be in the format of "
            + "DD/MM/YYYY HHMM, where time is represented in 24 hours";
    public static final SimpleDateFormat START_DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");

    public final Date value;

    /**
     * Validates given start date time.
     *
     * @throws IllegalValueException if given start date time string is invalid.
     */
    public StartDateTime(String startDateTime) throws IllegalValueException {
        assert startDateTime != null;
        String trimmedStartDateTime = startDateTime.trim();
        try {
            this.value = START_DATETIME_FORMATTER.parse(trimmedStartDateTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_START_DATETIME_CONSTRAINTS);
        }
    }

    /**
     * Returns if a given string is a valid start datetime.
     */
    public static boolean isValidStartDateTime(String test) {
        START_DATETIME_FORMATTER.setLenient(false);
        try {
            START_DATETIME_FORMATTER.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return START_DATETIME_FORMATTER.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                        && this.value.equals(((StartDateTime) other).value)); // state
                                                                              // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
