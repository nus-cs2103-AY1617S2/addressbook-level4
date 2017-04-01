//@@author A0144885R
package seedu.address.model.task.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a date with time.
 *
 * Date is default to be today
 */
public class DateTime extends DateValue {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
        "Allowed format for DateTime obj: 20/2/2012 10:12 pm";

    /**
    * Output format used to display deadline with both date and time.
    * Day, Month Date Year at Hour:Minute
    * Example: Tuesday, April 1 2013 at 23:59
    */
    public static final String READABLE_DATETIME_OUTPUT_FORMAT = "EEE, MMM dd yyyy, hh:mm aaa";

    public DateTime() {
        super(new Date());
    }

    public DateTime(Date date) {
        super(date);
    }

    public DateTime(DateValue other) {
        super(other);
    }

    /**
     * Combine date and time from 2 DateValue objects with date taken from the first
     * and time taken from the second.
     */
    public DateTime(Date date, Date time) {
        super(new Date(date.getYear(), date.getMonth(), date.getDate(),
                                time.getHours(), time.getMinutes()));
    }

    public DateTime getBeginning() {
        return new DateTime(this.date);
    }

    public DateTime getEnding() {
        return new DateTime(this.date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(READABLE_DATETIME_OUTPUT_FORMAT).format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateTime // instanceof handles nulls
                    && this.date.equals(((DateTime) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
