package seedu.address.model.task;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Represents a Task's date and time for a deadline or a starting time in the
 * task manager.
 */
public abstract class DateTime {

    PrettyTime dateTime;

    /**
     * @return a PrettyTime object
     */
    public PrettyTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Check if this object is after the referenced PrettyTime object
     *
     * @param dt
     *            the DateTime object you want to compare with
     * @return true for this date is after the referenced date
     */
    public Boolean isAfter(DateTime dt) {
        return this.dateTime.getReference().after(dt.getDateTime().getReference());
    }

    /**
     * @return a Calendar object referenced by this instance
     */
    public Date getDate() {
        return this.dateTime.getReference();
    }

    /**
     * @return the date & time in String
     */
    @Override
    public String toString() {
        return dateTime.toString();
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
}
