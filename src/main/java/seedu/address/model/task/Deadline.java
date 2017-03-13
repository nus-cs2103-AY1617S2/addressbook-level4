package seedu.address.model.task;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a Task's date and time for a deadline in the task manager.
 */
public class Deadline extends DateTime {
    private static final int END_OF_DAY_MINUTE = 59;
    private static final int END_OF_DAY_HOUR = 23;

    public Deadline(Date date, boolean isMissingTime, boolean isMissingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isMissingTime) {
            calendar.set(Calendar.MINUTE, END_OF_DAY_MINUTE);
            calendar.set(Calendar.HOUR, END_OF_DAY_HOUR);
            dateTime.format(calendar);
        } else if (isMissingDate) {
            Calendar today = Calendar.getInstance();
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            dateTime.format(calendar);
        } else {
            dateTime.format(calendar);
        }
    }

    /**
     * Set the time and date to this deadline
     *
     * @param date
     *            the new Date object this Deadline instance to be referenced to
     * @param isMissingTime
     *            whether the default deadline time to be used
     * @param isMissingDate
     *            whether the default deadline date to be used
     */
    public void set(Date date, boolean isMissingTime, boolean isMissingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isMissingTime) {
            calendar.set(Calendar.MINUTE, END_OF_DAY_MINUTE);
            calendar.set(Calendar.HOUR, END_OF_DAY_HOUR);
            dateTime.format(calendar);
        } else if (isMissingDate) {
            Calendar today = Calendar.getInstance();
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            dateTime.format(calendar);
        } else {
            dateTime.format(calendar);
        }
    }
}
