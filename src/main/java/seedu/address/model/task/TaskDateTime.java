package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModelManager;

/**
 * Represents a Task's due date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class TaskDateTime {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Due date should contain day/month/year hour:minute";

    private static final Logger logger = LogsCenter.getLogger(TaskDateTime.class);

    public final String value;
    public final Integer day;
    public final Integer month;
    public final Integer year;
    public final Integer hour;
    public final Integer minute;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskDateTime(String dateTime) throws IllegalValueException {
        if (!dateTime.equals("")) {
            String trimmedDateTime = dateTime.trim();
            Date parsedDateTime = parseDateTime(trimmedDateTime);
            this.value = trimmedDateTime;
            this.day = parsedDateTime.getDay();
            this.month = parsedDateTime.getMonth();
            this.year = parsedDateTime.getYear();
            this.hour = parsedDateTime.getHours();
            this.minute = parsedDateTime.getMinutes();
        } else {
            this.value = "";
            this.day = 0;
            this.month = 0;
            this.year = 0;
            this.hour = -1;
            this.minute = -1;
        }
    }

    /**
     * Returns object containing date and time given by string value
     * 
     * @param value
     * @return
     */
    public Date parseDateTime(String value) throws IllegalValueException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
                || (other instanceof Content // instanceof handles nulls
                        && this.value.equals(((Content) other).fullContent)); // state
                                                                              // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
