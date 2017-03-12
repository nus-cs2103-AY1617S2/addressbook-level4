package savvytodo.model.task;

import java.util.Calendar;
import java.util.Date;

import savvytodo.commons.exceptions.IllegalValueException;

/**
 * @author A0140016B
 *
 * Represents Task's DateTime in the task manager Guarantees: immutable;
 * is valid as declared in {@link #isValidDateTime(Date, Date)} *
 */
public class DateTime {

    public final Date startDateTime;
    public final Date endDateTime;

    private Calendar current;

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Start date/time should not be after End date/time";

    /**
     * Validates given DateTime.
     * @throws IllegalValueException if given DateTime is invalid.
     */
    public DateTime(Date startDateTime, Date endDateTime) throws IllegalValueException {
        current = Calendar.getInstance();
        current.setTime(new Date());
        if (!isValidDateTime(startDateTime, endDateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Returns true if a given string is a valid task dateTime.
     */
    public static boolean isValidDateTime(Date startDateTime, Date endDateTime) {
        if (startDateTime != null && endDateTime != null) {
            return startDateTime.before(endDateTime);
        } else {
            return false;
        }
    }

}
