package seedu.address.model.task;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a Task's date and time for a starting time in the task manager.
 */
public class StartingTime extends DateTime {
    private static final int START_OF_DAY_MINUTE = 0;
    private static final int START_OF_DAY_HOUR = 0;

    public StartingTime(Date date, boolean isMissingTime, boolean isMissingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isMissingTime) {
            calendar.set(Calendar.MINUTE, START_OF_DAY_MINUTE);
            calendar.set(Calendar.HOUR, START_OF_DAY_HOUR);
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
     *            the new Date object this StartingTime instance to be
     *            referenced to
     * @param isMissingTime
     *            whether the default starting time to be used
     * @param isMissingDate
     *            whether the default starting date to be used
     */
    public void set(Date date, boolean isMissingTime, boolean isMissingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isMissingTime) {
            calendar.set(Calendar.MINUTE, START_OF_DAY_MINUTE);
            calendar.set(Calendar.HOUR, START_OF_DAY_HOUR);
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
