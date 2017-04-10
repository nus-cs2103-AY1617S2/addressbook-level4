package seedu.task.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import com.google.api.services.calendar.model.EventDateTime;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in KIT. Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date format invalid, try dates like,"
                                                        + " tomorrow at 5pm or 4th April."
                                                        + " Check that Month is before date,"
                                                        + " MM/DD/YY or MM-DD-YY";
    private final java.util.Date value;
    private static PrettyTimeParser pretty = new PrettyTimeParser();
    private String extractedFrom = "";

    //@@author A0140063X
    public Date() {
        this.value = null;
    }

    //@@author A0140063X
    /**
     * Validates and creates date given a String.
     *
     * @param date  Date in String format.
     * @throws IllegalValueException If given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        if (date == null || date.trim().equals("")) {
            this.value = null;

        } else {
            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }

            List<java.util.Date> dates = pretty.parse(date);

            // Ignore seconds
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates.get(0));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long time = cal.getTimeInMillis();

            this.value = new java.util.Date(time);

            // used for find command
            extractedFrom = date;
        }
    }

    //@@author A0140063X
    /**
     * Creates a Date object from given eventDateTime. Used for Google Calendar.
     *
     * @param eventDateTime    EventDateTime to convert from.
     */
    public Date(EventDateTime eventDateTime) {
        if (eventDateTime == null) {
            this.value = null;
            return;
        }

        // Event might not have time
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
     *
     * @return  Date in java.util.Date format.
     */
    public java.util.Date getDateValue() {
        return this.value;
    }

    //@@author A0140063X
    /**
     *
     * @return  True if value is null.
     */
    public boolean isNull() {
        return this.value == null;
    }

    //@@author A0140063X
    /**
     * Returns true if a given string is a valid date.
     *
     * @param input     String to check.
     * @return          True if valid.
     */
    public static boolean isValidDate(String input) {
        List<java.util.Date> dates = pretty.parse(input);
        return !dates.isEmpty();
    }

    //@@author A0140063X
    /**
     * Compares two dates and returns true if date1 is before date2
     *
     * @param date1     First Date to compare.
     * @param date2     Second Date to compare.
     * @return          True if date1 is before date2.
     */
    public static boolean isBefore(Date date1, Date date2) {
        return date1.value.before(date2.value);
    }

    //@@author A0140063X
    /**
     * Display dates in this format: M/d/y h:mm a
     * Also adds relative time behind using PrettyTime display.
     *
     * @return Formatted date.
     */
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
    /**
     *
     * @return True if equals.
     */
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

    @SuppressWarnings("deprecation")
    public boolean equalsIgnoreMinutes(Date otherDate) {
        if (otherDate.value == null && this.value == null) {
            return true;
        } else if (otherDate.value == null || this.value == null) {
            return false;
        }
        return otherDate == this // short circuit if same object
                || (this.value.getDate() == otherDate.value.getDate()
                        && this.value.getMonth() == otherDate.value.getMonth()
                        && this.value.getYear() == otherDate.value.getYear()
                        && this.value.getHours() == otherDate.value.getHours());

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
