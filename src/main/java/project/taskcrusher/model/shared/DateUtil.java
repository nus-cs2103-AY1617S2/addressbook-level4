package project.taskcrusher.model.shared;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import project.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * Utility class for parsing Dates
 *
 */
public class DateUtil {

    public static final String MESSAGE_DATE_PASSED = "Dates provided must not be in the past";
    public static final String MESSAGE_DATE_AMBIGUOUS = "Multiple possible dates provided." +
        "Please provide one date in a supported format";
    public static final String MESSAGE_DATE_NOT_FOUND = "Input provided cannot be parsed as Date" +
        "Please provide one date in a supported format";;

    public static Date parseDate(String toParse) throws IllegalValueException {
        PrettyTimeParser dateParser = new PrettyTimeParser();
        List<Date> parseResults = dateParser.parse(toParse);

        if (parseResults.size() < 1) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }

        //TODO define formats to avoid ambiguous/multiple deadlines
        // deadlines
        // if (parsedDeadline.size() != 1) {
        // return false;
        // }

        Date parsed = parseResults.get(0);
        if (hasPassed(parsed)) {
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

    public static String toString(Date date) {
        assert date != null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mma");
        return sdf.format(date);
    }
}
