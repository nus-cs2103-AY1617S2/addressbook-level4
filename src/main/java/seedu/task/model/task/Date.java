package seedu.task.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import com.google.api.services.calendar.model.EventDateTime;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in KIT. Guarantees: immutable; is valid as declared
 * in {@link #isValidDate(String)}
 */
public class Date {

    // add to user guide
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, try dates like,"
                                                        + " tomorrow at 5pm or 4th April."
                                                        + " Check that Month is before date,"
                                                        + " MM/DD/YY or MM-DD-YY";
    public static final String DEFAULT_DATE = "DEFAULT_DATE";
    private final java.util.Date value;
    private static PrettyTimeParser pretty = new PrettyTimeParser();

    //Allows an empty constructor
    public Date() {
        this.value = null;
    }

    public Date(EventDateTime eventDateTime) {
        if (eventDateTime == null) {
            this.value = null;
        } else {
            this.value = new java.util.Date(eventDateTime.getDate().getValue());
        }
    }

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();

        if (date.equals(DEFAULT_DATE) || trimmedDate.equals("")) {
            this.value = null;
        } else {

            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }

            List<java.util.Date> dates = pretty.parse(date);
            Calendar cal = Calendar.getInstance(); // locale-specific
            cal.setTime(dates.get(0));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long time = cal.getTimeInMillis();
            this.value = new java.util.Date(time);
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {

        List<java.util.Date> dates = pretty.parse(input);

        if (dates.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNull() {
        return this.value == null;
    }

    @Override
    public String toString() {
        if (value == null) {
            return new String("");
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("M/d/y h:mm a");
        PrettyTime pretty = new PrettyTime();
        return displayFormat.format(value) + ", " + pretty.format(value);
    }

    @Override
    public boolean equals(Object other) {
        Date otherDate = ((Date) other);
        if (otherDate.value == null && this.value == null) {
            return true;
        } else if (otherDate.value == null || this.value == null) {
            return false;
        }
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.value.equals(((Date) other).value)); // state
                                                                     // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public java.util.Date getDateValue() {
        return this.value;
    }

    /**
     * Compares two dates and returns true if date1 is before date 2
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBefore(Date date1, Date date2) {
        return date1.value.before(date2.value);
    }
}
