package seedu.address.model.task.date;

import java.util.Date;

/**
 * An interface represents date and time.
 */
public abstract class DateValue {

    protected final Date date;

    public DateValue() {
        this.date = new Date();
    }

    public DateValue(Date date) {
        this.date = date;
    }

    public DateValue(DateValue dateValue) {
        this.date = dateValue.date;
    }

    /* Getters */
    public Date getValue() {
        return date;
    }

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonth();
    }

    public int getDate() {
        return date.getDate();
    }

    public int getHours() {
        return date.getHours();
    }

    public int getMinutes() {
        return date.getMinutes();
    }


    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateValue // instanceof handles nulls
                    && this.date.equals(((DateValue) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
