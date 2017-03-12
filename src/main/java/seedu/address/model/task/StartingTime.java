package seedu.address.model.task;

import java.util.Calendar;

/**
 * Represents a Task's date and time for a starting time in the task manager.
 */
public class StartingTime extends DateTime {
    private static final int START_OF_DAY_MINUTE = 0;
    private static final int START_OF_DAY_HOUR = 0;

    public StartingTime(Calendar date, boolean isMissingTime, boolean isMissingDate) {
        if (isMissingTime) {
            date.set(Calendar.MINUTE, START_OF_DAY_MINUTE);
            date.set(Calendar.HOUR, START_OF_DAY_HOUR);
            dateTime.format(date);
        } else if (isMissingDate) {
            Calendar today = Calendar.getInstance();
            date.set(Calendar.YEAR, today.get(Calendar.YEAR));
            date.set(Calendar.MONTH, today.get(Calendar.MONTH));
            date.set(Calendar.DATE, today.get(Calendar.DATE));
            dateTime.format(date);
        } else {
            dateTime.format(date);
        }
    }

    /**
     * Set the time and date to this deadline
     *
     * @param date
     *            the new Calendar object this StartingTime instance to be
     *            referenced to
     * @param isMissingTime
     *            whether the default starting time to be used
     * @param isMissingDate
     *            whether the default starting date to be used
     */
    public void set(Calendar date, boolean isMissingTime, boolean isMissingDate) {
        if (isMissingTime) {
            date.set(Calendar.MINUTE, START_OF_DAY_MINUTE);
            date.set(Calendar.HOUR, START_OF_DAY_HOUR);
            dateTime.format(date);
        } else if (isMissingDate) {
            Calendar today = Calendar.getInstance();
            date.set(Calendar.YEAR, today.get(Calendar.YEAR));
            date.set(Calendar.MONTH, today.get(Calendar.MONTH));
            date.set(Calendar.DATE, today.get(Calendar.DATE));
            dateTime.format(date);
        } else {
            dateTime.format(date);
        }
    }
}
