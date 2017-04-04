//@@author A0131125Y
package seedu.toluist.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Utility class for DateTime
 */
public class DateTimeUtil {
    //@@author A0127545A
    /**
     * Given a date string, parse it using Natty (NLP library for dates) and return a LocalDateTime
     * @param stringDate
     * @return LocalDateTime of the first Date returned by Natty
     */
    public static LocalDateTime parseDateString(String stringDate) {
        if (stringDate == null) {
            return null;
        }
        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(stringDate);
        if (dateGroups.isEmpty()) {
            return null;
        }
        DateGroup dateGroup = dateGroups.get(0);
        List<Date> dates = dateGroup.getDates();
        if (dates.isEmpty()) {
            return null;
        }
        Date date = dates.get(0);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //@@author A0131125Y
    /**
     * Check if datetime is from today
     *
     * @param dateTime a datetime
     * @return true / false
     */
    public static boolean isToday(LocalDateTime dateTime) {
        LocalDate today = LocalDate.now();
        LocalDate date = dateTime.toLocalDate();
        return date.equals(today);
    }

    /**
     * Check if datetime is from yesterday
     *
     * @param dateTime a datetime
     * @return true / false
     */
    public static boolean isYesterday(LocalDateTime dateTime) {
        LocalDate yesterday = LocalDate.now().plusDays(-1);
        LocalDate date = dateTime.toLocalDate();
        return date.equals(yesterday);
    }

    /**
     * Check if datetime is from tomorrow
     *
     * @param dateTime a datetime
     * @return true / false
     */
    public static boolean isTomorrow(LocalDateTime dateTime) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate date = dateTime.toLocalDate();
        return date.equals(tomorrow);
    }

    /**
     * Check if a dateTime is before or equal to another datetime
     * null dateTime is considered to be after
     * @param dateTime1
     * @param dateTime2
     * @return true / false
     */
    public static boolean isBeforeOrEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (Objects.equals(dateTime1, dateTime2)) {
            return true;
        }
        if (dateTime2 == null) {
            return true;
        }
        if (dateTime1 == null) {
            return false;
        }
        return dateTime1.isBefore(dateTime2);
    }
}
