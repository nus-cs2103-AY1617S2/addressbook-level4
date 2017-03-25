package seedu.task.commons.util;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author A0146789H
/**
 * @author amon
 *
 * This class provides utility functions to flexibly parse Dates from a string.
 */
public class NattyDateUtil {

    /**
     * Parse a string into a single date object using Natty.
     *
     * @param dataString
     * @return a Date object representing the input date
     */
    public static Date parseSingleDate(String dataString) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dataString);

        // Check if there are any results. If not, return null.
        if (groups.isEmpty() || groups.get(0).getDates().isEmpty()) {
            return null;
        }

        return groups.get(0).getDates().get(0);
    }

}
