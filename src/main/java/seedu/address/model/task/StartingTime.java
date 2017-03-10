package seedu.address.model.task;

import java.util.Calendar;

/**
 * Represents a Task's date and time for a starting time in the task manager.
 */
public class StartingTime extends DateTime {
    final static int START_OF_DAY_MINUTE = 0;
    final static int START_OF_DAY_HOUR = 0;

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
