package seedu.address.model.task;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Represents a Task's date and time for a starting time in the task manager.
 */
public class StartingTime extends DateTime {

    PrettyTime dateTime = new PrettyTime();

    public StartingTime(Date date) {
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

    @Override
    public PrettyTime getDateTime() {
        return this.dateTime;
    }

    @Override
    public Date getDate() {
        return this.dateTime.getReference();
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public String getDuration(Date date) {
        PrettyTime duration = new PrettyTime();
        return duration.format(this.dateTime.calculatePreciseDuration(date));
    }
}
