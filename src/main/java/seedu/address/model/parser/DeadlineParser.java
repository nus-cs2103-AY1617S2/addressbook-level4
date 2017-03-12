package seedu.address.model.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A Parser for Deadline class.
 */
public class DeadlineParser {

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

    /** Validate given string with all sets of date formats. */
    public static boolean isParsable(String dateString) {
        return isParsableDate(dateString)
                || isParsableTime(dateString)
                || isParsableDateTime(dateString);
    }

    /** Validate given string with given set of date formats. */
    public static boolean isParsable(String dateString, String[] dateFormats) {
        for (String dateFormat : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(dateFormat);
                df.setLenient(false);
                df.parse(dateString);
                return true;
            } catch (ParseException e) {
                // Do nothing
            }
        }
        return false;
    }

    public static boolean isParsableDate(String dateString) {
        return isParsable(dateString, DATE_VALIDATION_REGEX);
    }

    public static boolean isParsableTime(String dateString) {
        return isParsable(dateString, TIME_VALIDATION_REGEX);
    }

    public static boolean isParsableDateTime(String dateString) {
        return isParsable(dateString, getAllPossibleDateTimeFormats());
    }

    /**
     * Parses date string using given set of date formats.
     */
    public static Date parseString(String dateString, String[] dateFormats) {
        for (String dateFormat : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(dateFormat);
                return df.parse(dateString);
            } catch (ParseException e) {
                // Do nothing
            }
        }
        return null;
    }

    public static Date parseDateString(String dateString) {
        return parseString(dateString, DATE_VALIDATION_REGEX);
    }

    public static Date parseTimeString(String dateString) {
        return parseString(dateString, TIME_VALIDATION_REGEX);
    }

    public static Date parseDateTimeString(String dateString) {
        return parseString(dateString, getAllPossibleDateTimeFormats());
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
        for (String dateRegex : DATE_VALIDATION_REGEX) {
            for (String separator : DATE_TIME_SEPARATORS) {
                for (String timeRegex : TIME_VALIDATION_REGEX) {
                    dateFormats[index++] = dateRegex + separator + timeRegex;
                }
            }
        }
        return dateFormats;
    }

}
