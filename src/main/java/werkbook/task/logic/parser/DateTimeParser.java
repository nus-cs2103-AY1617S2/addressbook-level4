package werkbook.task.logic.parser;

import java.util.Date;
import java.util.List;
 
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import werkbook.task.model.task.EndDateTime;

//@@author A0139903B
/**
 * Parser that uses PrettyTime to parse a string and convert it to follow the date time format
 */
public class DateTimeParser {
    private static PrettyTimeParser p = new PrettyTimeParser();

    /**
     * Parses any string to check if any dates can be found
     * @param date Any string to be parsed
     * @return Returns formatted date time if a string was found, an empty string otherwise
     */
    public static String Parse(String date) {
        List<Date> dateList = p.parse(date);        
        return dateList.size() == 0 ? "" : EndDateTime.END_DATETIME_FORMATTER.format(dateList.get(0));
    }

    /**
     * Checks if the string is a valid date
     * @param date String to be checked
     * @return Returns true if the date is valid
     */
    public static boolean isValidDate(String date) {
        return p.parse(date).size() != 0 ? true : false;
    }
}
