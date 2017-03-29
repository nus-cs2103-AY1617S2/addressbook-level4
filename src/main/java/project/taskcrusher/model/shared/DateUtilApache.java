package project.taskcrusher.model.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Timeslot;

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

    public static final String[] PARSE_PATTERNS = { "yyyy-MM-dd hh:mma", "yyyy-MM-dd", "MM-dd hh:mma",
                                                    "hh:mma"};
    public static final int FORMAT_DATE_ABSOLUTE = 0;
    public static final int FORMAT_THIS_YEAR = 2;
    public static final int FORMAT_DATE_RELATIVE = 3;

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

    public static String deadlineAsStringForUi(Date date) {
        assert date != null;
        SimpleDateFormat formatter;
        String prepend = "";
        if (isToday(date)) {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[FORMAT_DATE_RELATIVE]);
            prepend = "Today ";
        } else if (isThisYear(date)) {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[FORMAT_THIS_YEAR]);
        } else {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[0]);
        }
        return prepend + formatter.format(date);
    }

    private static boolean isThisYear(Date d) {
        Date now = new Date();
        SimpleDateFormat yearChecker = new SimpleDateFormat("yyyy");
        return yearChecker.format(now).equals(yearChecker.format(d));
    }

    private static boolean isToday(Date d) {
        Date now = new Date();
        SimpleDateFormat dateChecker = new SimpleDateFormat("yyyyMMdd");
        return dateChecker.format(now).equals(dateChecker.format(d));
    }

    public static String dateAsStringForStorage(Date date) {
        assert date != null;
        SimpleDateFormat sdf = new SimpleDateFormat(PARSE_PATTERNS[0]);
        return sdf.format(date);
    }

    public static String timeslotAsStringForUi(Timeslot timeslot) {
        assert timeslot != null;
        String endFormat, startFormat, prepend = "";
        if (isSameDate(timeslot.start, timeslot.end)) {
            endFormat = PARSE_PATTERNS[FORMAT_DATE_RELATIVE];
        } else {
            endFormat = PARSE_PATTERNS[FORMAT_DATE_ABSOLUTE];
        }
        if (isToday(timeslot.start)) {
            startFormat = PARSE_PATTERNS[FORMAT_DATE_RELATIVE];
            prepend = "Today ";
        } else {
            startFormat = PARSE_PATTERNS[FORMAT_DATE_ABSOLUTE];
        }

        SimpleDateFormat sdf = new SimpleDateFormat(startFormat);
        prepend += sdf.format(timeslot.start) + " to ";
        sdf.applyPattern(endFormat);
        prepend += sdf.format(timeslot.end);
        return prepend;
    }

    private static boolean isSameDate(Date d1, Date d2) {
        SimpleDateFormat dateChecker = new SimpleDateFormat("yyyyMMdd");
        return dateChecker.format(d1).equals(dateChecker.format(d2));
    }
}

