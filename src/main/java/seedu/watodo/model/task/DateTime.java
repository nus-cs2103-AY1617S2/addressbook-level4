package seedu.watodo.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;

//@@author A0143076J
/**
 * Represents a Task's start time, end time or deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    private static final int INDEX_VALID_DATE = 0;
    private static final String DATESTRING_NOW = "now";
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date and time format must be a date/day, time or both";
    public static final Parser DATE_TIME_PARSER = new Parser(TimeZone.getDefault());  //Parser class in natty library

    public final Calendar dateTime;

    /**
     * Validates given DateTime.
     *
     * @throws IllegalValueException if given dateTime string is invalid.
     */
    public DateTime(String dateString) throws IllegalValueException {
        assert dateString != null;
        String trimmedDateString = dateString.trim();
        if (!isValidDateTime(trimmedDateString)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.dateTime = convertToCalendarFormat(trimmedDateString);
    }

    /**
     * Returns true if a given string is a valid date time.
     */
    public static boolean isValidDateTime(String dateTime) {
        List<DateGroup> parsedDateGroups = DATE_TIME_PARSER.parse(dateTime);
        return parsedDateGroups.size() == 1 && !parsedDateGroups.get(INDEX_VALID_DATE).getDates().isEmpty();
    }

    /**
     * Converts the given string into a standard Date format of year, month, date, hour, minutes and seconds.
     * Precondition: the String dateTime has already been checked to be valid
     */
    private Calendar convertToCalendarFormat(String dateString) {
        List<DateGroup> parsedDateGroups = DATE_TIME_PARSER.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDateGroups.get(INDEX_VALID_DATE).getDates().get(INDEX_VALID_DATE));

        //if no timing is given by the user, default timing of 11.59pm is set.
        Date currDate = new Date();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ssa", Locale.ENGLISH);
        if (!dateString.toLowerCase().equals(DATESTRING_NOW)) {
            if (timeFormatter.format(currDate).equals(timeFormatter.format(cal.getTime()))) {
                cal.set(Calendar.HOUR, 11);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 00);
                cal.set(Calendar.MILLISECOND, 00);
                cal.set(Calendar.AM_PM, Calendar.PM);
            }
        }
        return cal;
    }

    /* Checks if the current DateTime is at a later date than another given DateTime */
    public boolean isLater(DateTime other) {
        return this.dateTime.after(other.dateTime);
    }

    @Override
    public String toString() {
        Date currDate = new Date();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(currDate);

        if (dateTime.get(Calendar.YEAR) != (currCal.get(Calendar.YEAR))) {
            //only shows the year if the date is not in the current year
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE d MMM yy, h.mma", Locale.ENGLISH);
            return dateFormatter.format(dateTime.getTime());
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE d MMM, h.mma", Locale.ENGLISH);
        return dateFormatter.format(dateTime.getTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
