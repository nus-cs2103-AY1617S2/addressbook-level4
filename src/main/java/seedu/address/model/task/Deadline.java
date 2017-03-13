package seedu.address.model.task;

import java.util.Calendar;

/**
 * Represents a Task's date and time for a deadline in the task manager.
 */
public class Deadline extends DateTime {
    private static final int END_OF_DAY_MINUTE = 59;
    private static final int END_OF_DAY_HOUR = 23;

    public Deadline(Calendar date, boolean isMissingTime, boolean isMissingDate) {
        if (isMissingTime) {
            date.set(Calendar.MINUTE, END_OF_DAY_MINUTE);
            date.set(Calendar.HOUR, END_OF_DAY_HOUR);
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
     *            the new Calendar object this Deadline instance to be
     *            referenced to
     * @param isMissingTime
     *            whether the default deadline time to be used
     * @param isMissingDate
     *            whether the default deadline date to be used
     */
    public void set(Calendar date, boolean isMissingTime, boolean isMissingDate) {
        if (isMissingTime) {
            date.set(Calendar.MINUTE, END_OF_DAY_MINUTE);
            date.set(Calendar.HOUR, END_OF_DAY_HOUR);
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
