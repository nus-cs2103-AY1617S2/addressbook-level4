package werkbook.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task End DateTime in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDateTime(String)}
 */
public class EndDateTime {

    public static final String MESSAGE_END_DATETIME_CONSTRAINTS = "End Date/Time must be in the format of "
            + "DD/MM/YYYY HHMM, where time is represented in 24 hours";
    public static final SimpleDateFormat END_DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HHmm");
    public static final SimpleDateFormat LENIENT_FORMATTER = new SimpleDateFormat("dd/MM");

    public final Optional<Date> value;

    /**
     * Validates given start datetime.
     *
     * @throws IllegalValueException if given end date time string is invalid.
     */
    public EndDateTime(String endDateTime) throws IllegalValueException {
        assert endDateTime != null;
        String trimmedEndDateTime = endDateTime.trim();
        if (trimmedEndDateTime.equals("")) {
            this.value = Optional.empty();
        } else {
            try {
                this.value = Optional.of(END_DATETIME_FORMATTER.parse(trimmedEndDateTime));
            } catch (ParseException e) {
                throw new IllegalValueException(MESSAGE_END_DATETIME_CONSTRAINTS);
            }
        }
    }

    /**
     * Returns if a given string is a valid end datetime.
     * @throws IllegalValueException
     */
    public static boolean isValidEndDateTime(String test) throws IllegalValueException {
        END_DATETIME_FORMATTER.setLenient(false);
        try {
            END_DATETIME_FORMATTER.parse(test);
        } catch (ParseException e) {
            return false;
            //throw new IllegalValueException(MESSAGE_END_DATETIME_CONSTRAINTS);
        }
        return true;
    }

    public boolean isPresent() {
        return this.value.isPresent();
    }

    @Override
    public String toString() {
        if (this.value.isPresent()) {
            return END_DATETIME_FORMATTER.format(this.value.get());
        } else {
            return "";
        }
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
