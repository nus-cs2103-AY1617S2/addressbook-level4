package seedu.task.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0163848R
/**
 * Class of date-related methods
 */
public class DateUtil {

    /**
     * Parses string and attempts to transform it into a date
     * @param Natural-language string to interpret date from
     * @return java.util.Date created from passed string
     * @throws IllegalValueException
     */
    public static Date parse(String rawDate) throws IllegalValueException {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawDate);
            Date date = groups.get(0).getDates().get(0);
            return date;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(seedu.task.model.task.Date.MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Returns new date past given date by given days
     * @param Date to start at
     * @param Days to add
     * @return New date past given date by given days
     */
    public static Date add(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

//@@author A0164032U
    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(date);
            boolean x = (groups.get(0).getDates().get(0) != null);
            return x;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
