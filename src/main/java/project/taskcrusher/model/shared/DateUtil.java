package project.taskcrusher.model.shared;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import project.taskcrusher.commons.exceptions.IllegalValueException;

//@@author A0163962X
/**
 * Utility class for parsing Dates
 *
 */
public class DateUtil {

    public static final String MESSAGE_DATE_PASSED = "Dates provided must not be in the past";
    public static final String MESSAGE_DATE_AMBIGUOUS = "Multiple dates provided."
            + "Please provide one date in a supported format";
    public static final String MESSAGE_DATE_NOT_FOUND = "Input provided cannot be parsed as Date"
            + "Please provide one date in a supported format";;

    public static final String[] PARSE_PATTERNS = { "yyyy-MM-dd hh:mma", "yyyy-MM-dd", "MM-dd hh:mma", "hh:mma" };
    public static final int FORMAT_DATE_ABSOLUTE = 0;
    public static final int FORMAT_THIS_YEAR = 2;
    public static final int FORMAT_DATE_RELATIVE = 3;

    public static Date parseDate(String toParse) throws IllegalValueException {

        Parser nattyParser = new Parser();
        Date parsed = null;
        boolean needsTimeAdjustment = false;

        if (nattyParser.parse(toParse).size() < 1) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }
        List<Date> tempDateList = nattyParser.parse(toParse).get(0).getDates();

        if (tempDateList != null && tempDateList.size() > 0) {
            parsed = tempDateList.get(0);
            needsTimeAdjustment = nattyParser.parse(toParse).get(0).isTimeInferred();
        } else {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }

        if (needsTimeAdjustment) {
            parsed = DateUtils.setHours(parsed, 23);
            parsed = DateUtils.setMinutes(parsed, 59);
            parsed = DateUtils.setSeconds(parsed, 59);
            parsed = DateUtils.setMilliseconds(parsed, 59);
        }

        return parsed;
    }

    public static DateGroup parseDateAsDateGroup(String toParse) throws IllegalValueException {

        Parser nattyParser = new Parser();

        if (nattyParser.parse(toParse).size() < 1) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }

        List<Date> tempDateList = nattyParser.parse(toParse).get(0).getDates();

        if (tempDateList == null || tempDateList.size() <= 0) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }

        return nattyParser.parse(toParse).get(0);
    }

    public static String dateAsStringForStorage(Date date) {
        assert date != null;
        SimpleDateFormat sdf = new SimpleDateFormat(PARSE_PATTERNS[0]);
        return sdf.format(date);
    }

}
