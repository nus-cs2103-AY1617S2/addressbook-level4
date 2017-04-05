//@@author A0105748B
package seedu.bulletjournal.logic.parser;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * A Parser class that implements natty library to convert string objects into
 * dates. Returns a null date object if the argument is not recognized as a
 * date.
 */
public class DateParser {

    private static Parser dateParser = new Parser(TimeZone.getDefault());

    /**
     * Parses date object from a string. Only parses first recognised date
     * instance.
     */
    public static Date parse(String date) {
        List<DateGroup> parsedDates = dateParser.parse(date);

        if (parsedDates != null && !parsedDates.isEmpty()) {
            return parsedDates.get(0).getDates().get(0);
        } else {
            return null;
        }
    }
}
