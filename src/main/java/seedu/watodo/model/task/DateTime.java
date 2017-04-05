package seedu.watodo.model.task;

import java.util.Date;
import java.util.List;
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

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date and time format must be a date/day, time or both";
    public static final String MESSAGE_DATETIME_START_LATER_THAN_END = "End date must be later than start date!";
    public static final Parser DATE_TIME_PARSER = new Parser(TimeZone.getDefault());


    public final Date dateTime;

    /**
     * Validates given DateTime.
     *
     * @throws IllegalValueException if given dateTime string is invalid.
     */
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        String trimmedDateTime = dateTime.trim();
        if (!isValidDateTime(trimmedDateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.dateTime = convertToDateFormat(trimmedDateTime);
    }

    /**
     * Returns true if a given string is a valid date time.
     */
    public static boolean isValidDateTime(String dateTime) {
        List<DateGroup> parsedDateGroups = DATE_TIME_PARSER.parse(dateTime);
        return parsedDateGroups.size() == 1 && !parsedDateGroups.get(0).getDates().isEmpty();
    }

    /**
     * Converts the given string into a standard Date format of year, month, date, hour, minutes and seconds.
     * Precondition: the String dateTime has already been checked to be valid
     */
    private Date convertToDateFormat(String dateTime) {
        List<DateGroup> parsedDateGroups = DATE_TIME_PARSER.parse(dateTime);
        return parsedDateGroups.get(0).getDates().get(0);
    }

    /* Checks if the current DateTime is at a later date than another given DateTime */
    public boolean isLater(DateTime other) {
        return this.dateTime.after(other.dateTime);
    }

    @Override
    public String toString() {
        return dateTime.toString();
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
