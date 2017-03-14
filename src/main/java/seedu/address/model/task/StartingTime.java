package seedu.address.model.task;

import java.util.Calendar;
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
        return new PrettyTime().format(this.dateTime.getReference());
    }

    @Override
    public String getDuration(Date date) {
        PrettyTime duration = new PrettyTime();
        return duration.format(this.dateTime.calculatePreciseDuration(date));
    }

    @Override
    public boolean isSameDay(Date date) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(dateTime.getReference());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
                .get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
