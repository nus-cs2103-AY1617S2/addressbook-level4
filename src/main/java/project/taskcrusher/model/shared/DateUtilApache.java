package project.taskcrusher.model.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import project.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * Utility class for parsing Dates
 *
 */
public class DateUtilApache {

    public static final String MESSAGE_DATE_PASSED = "Dates provided must not be in the past";
    public static final String MESSAGE_DATE_AMBIGUOUS = "Multiple dates provided."
            + "Please provide one date in a supported format";
    public static final String MESSAGE_DATE_NOT_FOUND = "Input provided cannot be parsed as Date"
            + "Please provide one date in a supported format";;

    public static final String[] PARSE_PATTERNS = { "yyyy-MM-dd hh:mma", "yyyy-MM-dd" };

    public static Date parseDate(String toParse, boolean isNew) throws IllegalValueException {

        Date parsed = null;

        try {
            parsed = DateUtils.parseDateStrictly(toParse, PARSE_PATTERNS);
        } catch (ParseException p) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }

        if (isNew && hasPassed(parsed)) { // short circuit if not new/from
                                          // storage
            throw new IllegalValueException(MESSAGE_DATE_PASSED);
        }

        return parsed;
    }

    public static boolean hasPassed(Date date) {
        assert date != null;
        Date rightNow = new Date();
        if (date.before(rightNow)) {
            return true;
        } else {
            return false;
        }
    }

    public static String dateAsString(Date date) {
        assert date != null;
        SimpleDateFormat sdf = new SimpleDateFormat(PARSE_PATTERNS[0]);
        return sdf.format(date);
    }
}

