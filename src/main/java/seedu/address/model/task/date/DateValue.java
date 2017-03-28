//@@author A0144885R
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
        this.date = new Date(date.getYear(), date.getMonth(), date.getDate(),
                                date.getHours(), date.getMinutes());
    }

    public DateValue(DateValue dateValue) {
        this(dateValue.getValue());
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

    public int getDay() {
        return date.getDay();
    }

    public int getHours() {
        return date.getHours();
    }

    public int getMinutes() {
        return date.getMinutes();
    }

    public int getSeconds() {
        return date.getSeconds();
    }

    public long getTime() {
        return date.getTime();
    }

    /* Setters */
    public DateValue setYear(int year) {
        this.date.setYear(year);
        return this;
    }

    public DateValue setMonth(int month) {
        this.date.setMonth(month);
        return this;
    }

    public DateValue setDate(int date) {
        this.date.setDate(date);
        return this;
    }

    public DateValue setHours(int hours) {
        this.date.setHours(hours);
        return this;
    }

    public DateValue setMinutes(int minutes) {
        this.date.setMinutes(minutes);
        return this;
    }

    public DateValue setSeconds(int seconds) {
        this.date.setSeconds(seconds);
        return this;
    }

    public DateValue setTime(long milliseconds) {
        this.date.setTime(milliseconds);
        return this;
    }

    public boolean after(DateValue date) {
        return this.date.after(date.getValue());
    }

    public boolean before(DateValue date) {
        return this.date.before(date.getValue());
    }

    public abstract DateValue getBeginning();
    public abstract DateValue getEnding();

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
