package seedu.address.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public enum DEADLINE_TYPES {
        DATEONLY, DATETIME
    };

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Task deadline should strictly follow this format DD/MM/YYYY";

    public static final String[] DATE_VALIDATION_REGEX = {
        "dd-MM-yy",
        "dd/MM/yy",
        "dd-MM-yyyy",
        "dd/MM/yyyy",
        "EEE, MMM dd yyyy",
        "EEE, dd MMM yyyy"
    };

    public static final String[] TIME_VALIDATION_REGEX = {
        "KK:mm aaa",
        "hh:mm aaa",
        "HH:mm"
    };

    // Separators used to combine date and time
    public static final String[] DATE_TIME_SEPARATORS = {
        ", ",
        " "
    };

    /**
     * Output format used to display deadline with both date and time.
     * Day, Month Date Year at Hour:Minute
     * Example: Tuesday, April 1 2013 at 23:59
     */
    public static final String READABLE_DATETIME_OUTPUT_FORMAT = "EEE, MMM dd yyyy, hh:mm aaa";

    /**
     * Output format used to display deadline with only date.
     * Day, Month Date Year
     * Example: Tuesday, April 1 2013
     */
    public static final String READABLE_DATEONLY_OUTPUT_FORMAT = "EEE, MMM dd yyyy";

    private Date date;
    private DEADLINE_TYPES type;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String dateString) throws IllegalValueException {
        assert dateString != null;
        // Trim and remove continuous whitespace
        String trimmedDateString = dateString.trim().replace(" +", " ");
        updateValue(trimmedDateString);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeadline(String test) {
        return isValidDeadline(test, DATE_VALIDATION_REGEX)
                || isValidDeadline(test, TIME_VALIDATION_REGEX)
                || isValidDeadline(test, getAllPossibleDateTimeFormats());
    }

    /** Validate given string with given set of date formats */
    public static boolean isValidDeadline(String test, String[] dateFormats) {
        for(String dateFormat : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(dateFormat);
                df.setLenient(false);
                df.parse(test);
                return true;
            } catch (ParseException e) {
                // Do nothing
            }
        }
        return false;
    }

    /** Update object's values */
    public void updateValue(String dateString) throws IllegalValueException {

        String[] completeDateFormats = getAllPossibleDateTimeFormats();

        // Try date-only format
        if (isValidDeadline(dateString, DATE_VALIDATION_REGEX)) {
            date = parseDateString(dateString, DATE_VALIDATION_REGEX);
            type = DEADLINE_TYPES.DATEONLY;

        // Try time-only format
        } else if (isValidDeadline(dateString, TIME_VALIDATION_REGEX)) {
            date = parseDateString(dateString, TIME_VALIDATION_REGEX);

            // Date is missing so we provide one
            Date today = new Date();
            date.setDate(today.getDate());
            date.setMonth(today.getMonth());
            date.setYear(today.getYear());

            type = DEADLINE_TYPES.DATETIME;

        // Try complete date-time formats
        } else if (isValidDeadline(dateString, completeDateFormats)) {
            date = parseDateString(dateString, completeDateFormats);
            type = DEADLINE_TYPES.DATETIME;

        } else {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Parses date string with given set of date formats.
     */
    public Date parseDateString(String dateString, String[] dateFormats) {
        for(String dateFormat : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(dateFormat);
                return df.parse(dateString);
            } catch (ParseException e) {
                // Do nothing
            }
        }
        return null;
    }

    /**
     * Returns all combinations of date, time and separator
     */
    public static String[] getAllPossibleDateTimeFormats() {
        // Init date formats
        int dateFormatsCount = DATE_VALIDATION_REGEX.length
                                * DATE_TIME_SEPARATORS.length
                                * TIME_VALIDATION_REGEX.length;
        String[] dateFormats = new String[dateFormatsCount];

        // Add all combinations of time and date
        int index = 0;
        for(String dateRegex : DATE_VALIDATION_REGEX) {
            for(String separator : DATE_TIME_SEPARATORS) {
                for(String timeRegex : TIME_VALIDATION_REGEX) {
                    dateFormats[index++] = dateRegex + separator + timeRegex;
                }
            }
        }
        return dateFormats;
    }


    @Override
    public String toString() {
        switch (type) {
            case DATETIME:
                return new SimpleDateFormat(READABLE_DATETIME_OUTPUT_FORMAT).format(date);

            case DATEONLY:
                return new SimpleDateFormat(READABLE_DATEONLY_OUTPUT_FORMAT).format(date);

            default:
                return date.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
                    && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
