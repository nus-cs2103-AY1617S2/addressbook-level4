package seedu.toluist.commons.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Represent date nicely in human-readable format.
 */
public class DateTimeUtil {

    public static LocalDateTime toDate(String stringDate) {
        if (stringDate == null) {
            return null;
        } else {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(stringDate);
            if (dateGroups.isEmpty()) {
                return null;
            } else {
                DateGroup dateGroup = dateGroups.get(0);
                List<Date> dates = dateGroup.getDates();
                if (dates.isEmpty()) {
                    return null;
                } else {
                    Date date = dates.get(0);
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                }
            }
        }
    }

    public static String toString(LocalDateTime dateTime) {
        String stringDateTime = dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu, h:mm a"));
        return stringDateTime;
    }
}
