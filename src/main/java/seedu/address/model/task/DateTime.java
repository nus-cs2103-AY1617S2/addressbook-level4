package seedu.address.model.task;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Represents a Task's date and time for a deadline or a starting time in the
 * task manager.
 */
public abstract class DateTime {

    /**
     * @return a PrettyTime object
     */
    public abstract PrettyTime getDateTime();

    /**
     * @return a Calendar object referenced by this instance
     */
    public abstract Date getDate();

    /**
     * @return the date & time in String
     */
    @Override
    public abstract String toString();

    /**
     * get the duration between this date time and the specified date time
     *
     * @param date
     *            the other end of date
     * @return the duration in plain English
     */
    public abstract String getDuration(Date date);

    public abstract boolean isSameDay(Date date);
}
