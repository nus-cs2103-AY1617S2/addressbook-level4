//@@author A0144885R
package seedu.address.model.task.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a date with time.
 *
 * Date is default to be today
 */
public class DateOnly extends DateValue {

    public static final String MESSAGE_DATEONLY_CONSTRAINTS =
        "Allowed format for DateOnly obj: 20/2/2012";

    /**
    * Output format used to display deadline with both date and time.
    * Day, Month Date Year at Hour:Minute
    * Example: Tuesday, April 1 2013 at 23:59
    */
    public static final String READABLE_DATEONLY_OUTPUT_FORMAT = "EEE, MMM dd yyyy";

    public DateOnly() {
        super(new Date());
    }

    public DateOnly(Date date) {
        super(date);
    }

    public DateOnly(DateValue other) {
        super(other);
    }

    public DateOnly getBeginning() {
        Date date = new Date(getYear(), getMonth(), getDate(), 0, 0, 0);
        return new DateOnly(date);
    }

    public DateOnly getEnding() {
        Date date = new Date(getYear(), getMonth(), getDate(), 23, 59, 59);
        return new DateOnly(date);
    }

    @Override
    public boolean after(DateValue date) {
        return getBeginning().getValue().after(date.getValue());
    }

    @Override
    public boolean before(DateValue date) {
        return getEnding().getValue().before(date.getValue());
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(READABLE_DATEONLY_OUTPUT_FORMAT).format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateOnly // instanceof handles nulls
                    && this.date.equals(((DateOnly) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
