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

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, try dates like,"
                                                        + " tomorrow at 5pm or 4th April."
                                                        + " Check that Month is before date,"
                                                        + " MM/DD/YY or MM-DD-YY";
    private final java.util.Date value;
    private static PrettyTimeParser pretty = new PrettyTimeParser();
    private String extractedFrom;

    //@@author A0140063X
    public Date() {
        this.value = null;
    }

    //@@author A0140063X
    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        if (date == null || date.trim().equals("")) {
            this.value = null;

        } else {
            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }

            List<java.util.Date> dates = pretty.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates.get(0));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long time = cal.getTimeInMillis();

            this.value = new java.util.Date(time);

            //used for find command
            extractedFrom = date;
        }
    }

    //@@author A0140063X
    public Date(EventDateTime eventDateTime) {
        if (eventDateTime == null) {
            this.value = null;
            return;
        }

        if (eventDateTime.getDateTime() != null) {
            this.value = new java.util.Date(eventDateTime.getDateTime().getValue());
        } else if (eventDateTime.getDate() != null) {
            this.value = new java.util.Date(eventDateTime.getDate().getValue());
        } else {
            this.value = null;
        }
    }

    //@@author A0140063X
    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {
        List<java.util.Date> dates = pretty.parse(input);
        return !dates.isEmpty();
    }

    //@@author A0140063X
    public java.util.Date getDateValue() {
        return this.value;
    }

    //@@author A0140063X
    public boolean isNull() {
        return this.value == null;
    }

    //@@author A0140063X
    /**
     * Compares two dates and returns true if date1 is before date2
     * @param date1
     * @param date2
     */
    public static boolean isBefore(Date date1, Date date2) {
        return date1.value.before(date2.value);
    }

    //@@author A0140063X
    @Override
    public String toString() {
        if (value == null) {
            return new String("");
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("M/d/y h:mm a");
        PrettyTime pretty = new PrettyTime();
        return displayFormat.format(value) + ", " + pretty.format(value);
    }

    //@@author A0140063X
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

    //@@author
    @SuppressWarnings("deprecation")
    public boolean equalsIgnoreTime(Date otherDate) {
        if (otherDate.value == null && this.value == null) {
            return true;
        } else if (otherDate.value == null || this.value == null) {
            return false;
        }
        return otherDate == this // short circuit if same object
                || (this.value.getDate() == otherDate.value.getDate()
                && this.value.getMonth() == otherDate.value.getMonth()
                && this.value.getYear() == otherDate.value.getYear());

    }

    //@@author
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getExtractedFrom() {
        return this.extractedFrom;
    }
}
