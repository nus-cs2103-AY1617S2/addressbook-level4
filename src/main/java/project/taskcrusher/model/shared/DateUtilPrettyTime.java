package project.taskcrusher.model.shared;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import project.taskcrusher.commons.exceptions.IllegalValueException;

//@@author A0163962X-unused
/**
 * Utility class for parsing Dates
 *
 */
public class DateUtilPrettyTime {

    public static final String MESSAGE_DATE_PASSED = "Dates provided must not be in the past";
    public static final String MESSAGE_DATE_AMBIGUOUS = "Multiple dates provided." +
        "Please provide one date in a supported format";
    public static final String MESSAGE_DATE_NOT_FOUND = "Input provided cannot be parsed as Date" +
        "Please provide one date in a supported format";;

    public static Date parseDate(String toParse, boolean isNew) throws IllegalValueException {
        PrettyTimeParser dateParser = new PrettyTimeParser();
        List<Date> parseResults = dateParser.parse(toParse);

        if (parseResults.size() < 1) {
            throw new IllegalValueException(MESSAGE_DATE_NOT_FOUND);
        }  else if (parseResults.size() > 1) {
            throw new IllegalValueException(MESSAGE_DATE_AMBIGUOUS);
        }

        Date parsed = parseResults.get(0);
        if (isNew && hasPassed(parsed)) { //short circuit if not new/from storage
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mma");
        return sdf.format(date);
    }
}
