package seedu.toluist.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to assist in formatting date
 */
public class DateTimeFormatterUtil {
    public static final String TASK_DEADLINE = "by ";
    public static final String EVENT_TO = " to ";
    public static final String YESTERDAY = "yesterday";
    public static final String TODAY = "today";
    public static final String TOMORROW = "tomorrow";
    public static final String FORMAT_DATE = "dd MMM yyy";
    public static final String FORMAT_TIME = "hh:mm a";
    public static final String DATE_TIME_SEPARATOR = ", ";

    /**
     * Format task deadline
     */
    public static String formatTaskDeadline(LocalDateTime deadline) {
        return TASK_DEADLINE + formatDate(deadline) + DATE_TIME_SEPARATOR + formatTime(deadline);
    }

    /**
     * Format event range
     */
    public static String formatEventRange(LocalDateTime from, LocalDateTime to) {
        String dateFrom = formatDate(from);
        String dateTo = formatDate(to);
        String timeFrom = formatTime(from);
        String timeTo = formatTime(to);

        if (dateFrom.equals(dateTo)) {
            return dateFrom + DATE_TIME_SEPARATOR + timeFrom + EVENT_TO + timeTo;
        } else {
            return dateFrom + DATE_TIME_SEPARATOR + timeFrom + EVENT_TO + dateTo + DATE_TIME_SEPARATOR + timeTo;
        }
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (DateTimeUtil.isToday(dateTime)) {
            return TODAY;
        } else if (DateTimeUtil.isTomorrow(dateTime)) {
            return TOMORROW;
        } else if (DateTimeUtil.isYesterday(dateTime)) {
            return YESTERDAY;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        return dateTime.format(formatter);
    }


    public static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TIME);
        return dateTime.format(formatter);
    }
}
