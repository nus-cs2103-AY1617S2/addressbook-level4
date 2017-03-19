package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class TaskDateTime {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Due date should contain valid date in format "
            + "day/month/year hour:minute";

    public final String value;
    public final Date date;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskDateTime(String dateTime) throws IllegalValueException {

        String trimmedDateTime = dateTime.trim();
        if (!isThereDateTimeInInput(trimmedDateTime)) {
            this.value = trimmedDateTime;
            this.date = null;
        } else if (!isValidDateTime(trimmedDateTime)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
        } else {
            Date parsedDateTime = parseDateTime(trimmedDateTime);
            this.value = trimmedDateTime;
            this.date = parsedDateTime;
        }
    }

    /**
     * Returns true if a given string is valid date
     */
    public static boolean isValidDateTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setLenient(false);
        try {
            format.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if input string is not null
     */
    public static boolean isThereDateTimeInInput(String value) {
        return !value.equals("");
    }

    /**
     * Returns true if this TaskDateTime contains non-null date and time
     */
    public boolean isThereDateTime() {
        return !this.value.equals("");
    }

    /**
     * Returns object containing date and time given by string value
     * @param value
     * @return
     */
    public Date parseDateTime(String value) throws IllegalValueException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setLenient(false);
        Date result;
        try {
            result = format.parse(value);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        return result;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDateTime // instanceof handles nulls
                        && this.value.equals(((TaskDateTime) other).value)); // state
                                                                              // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
