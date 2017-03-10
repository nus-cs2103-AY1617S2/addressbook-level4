package seedu.toluist.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utilility class for date
 */
public class DateUtil {
    /**
     * Check if datetime is from today
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
     * @param dateTime a datetime
     * @return true / false
     */
    public static boolean isTomorrow(LocalDateTime dateTime) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate date = dateTime.toLocalDate();
        return date.equals(tomorrow);
    }
}
