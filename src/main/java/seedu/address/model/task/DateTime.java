package seedu.address.model.task;

import java.util.Calendar;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Represents a Task's date and time for a deadline or a starting time in the
 * task manager.
 */
public class DateTime implements Comparable<DateTime> {

    PrettyTime dateTime = new PrettyTime();

    public DateTime(Date date) {
        dateTime.setReference(date);
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
    public void set(Date date) {
        dateTime.setReference(date);
    }

    /**
     * @return a PrettyTime object
     */
    public PrettyTime getDateTime() {
        return this.dateTime;
    }

    /**
     * @return a copy of Date object referenced by this instance
     */
    public Date getDate() {
        return new Date(dateTime.getReference().getTime());
    }

    /**
     * @return the date & time in String
     */
    @Override
    public String toString() {
        return new PrettyTime().format(this.dateTime.getReference());
    }

    /**
     * get the duration between this date time and the specified date time
     *
     * @param date
     *            the other end of date
     * @return the duration in plain English
     */
    public String getDuration(Date date) {
        PrettyTime duration = new PrettyTime();
        return duration.format(this.dateTime.calculatePreciseDuration(date));
    }

    // @@author A0093999Y
    /**
     * Check if same day
     *
     * @param date
     *            the other date
     * @return whether it is the same day
     */
    public boolean isSameDay(Date date) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(dateTime.getReference());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public int compareTo(DateTime o) {
        return this.getDate().compareTo(o.getDate());
    }
}
