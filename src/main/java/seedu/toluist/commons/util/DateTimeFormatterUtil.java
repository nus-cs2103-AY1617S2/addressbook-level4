package seedu.toluist.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.toluist.model.Task.RecurringFrequency;

/**
 * A class to assist in formatting date
 */
public class DateTimeFormatterUtil {
    public static final String TO = "to";
    public static final String UNTIL = "until";
    public static final String TASK_DEADLINE = "by ";
    public static final String EVENT_TO = " to ";
    public static final String YESTERDAY = "yesterday";
    public static final String TODAY = "today";
    public static final String TOMORROW = "tomorrow";
    public static final String DAILY = "Every day";
    public static final String WEEKLY = "Every week";
    public static final String MONTHLY = "Every month";
    public static final String YEARLY = "Every year";
    public static final String FORMAT_DAY_OF_WEEK = "EEEE";
    public static final String FORMAT_DAY_OF_MONTH = "d";
    public static final String FORMAT_DATE_OF_YEAR = "dd MMMM";
    public static final String FORMAT_DATE = "E, dd MMM yyy";
    public static final String FORMAT_TIME = "hh:mm a";
    public static final String DATE_TIME_SEPARATOR = ", ";
    public static final String RECURRING_DATE_SEPARATOR = " ";
    public static final String SUFFIX_FIRST = "st";
    public static final String SUFFIX_SECOND = "nd";
    public static final String SUFFIX_THIRD = "rd";
    public static final String SUFFIX_FOURTH_ONWARDS = "th";

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

    /**
     * Format recurring task with deadline
     */
    public static String formatRecurringTaskDeadline(LocalDateTime deadline, LocalDateTime recurringEndDateTime,
            RecurringFrequency recurringFrequency) {
        String formattedResult = "";
        switch (recurringFrequency) {
        case DAILY:
            formattedResult += DAILY + RECURRING_DATE_SEPARATOR + formatTime(deadline);
            break;
        case WEEKLY:
            formattedResult += WEEKLY + RECURRING_DATE_SEPARATOR + formatDayOfWeek(deadline);
            break;
        case MONTHLY:
            formattedResult += MONTHLY + RECURRING_DATE_SEPARATOR + formatDayOfMonth(deadline);
            break;
        case YEARLY:
            formattedResult += YEARLY + RECURRING_DATE_SEPARATOR + formatDateOfYear(deadline);
            break;
        default:
            // won't happen
        }
        if (recurringEndDateTime != null) {
            formattedResult += RECURRING_DATE_SEPARATOR + UNTIL
                    + RECURRING_DATE_SEPARATOR + formatDate(recurringEndDateTime);
        }
        return formattedResult;
    }

    /**
     * Format recurring event
     */
    public static String formatRecurringEvent(LocalDateTime from, LocalDateTime to, LocalDateTime recurringEndDateTime,
            RecurringFrequency recurringFrequency) {
        String formattedResult = "";
        switch (recurringFrequency) {
        case DAILY:
            formattedResult += DAILY;
            if (formatTime(from).equals(formatTime(to))) {
                formattedResult += RECURRING_DATE_SEPARATOR + formatTime(from);
            } else {
                formattedResult += RECURRING_DATE_SEPARATOR + formatTime(from) + RECURRING_DATE_SEPARATOR + TO
                                 + RECURRING_DATE_SEPARATOR + formatTime(to);
            }
            break;
        case WEEKLY:
            formattedResult += WEEKLY;
            if (formatDayOfWeek(from).equals(formatDayOfWeek(to))) {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDayOfWeek(from);
            } else {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDayOfWeek(from) + RECURRING_DATE_SEPARATOR + TO
                                 + RECURRING_DATE_SEPARATOR + formatDayOfWeek(to);
            }
            break;
        case MONTHLY:
            formattedResult += MONTHLY;
            if (formatDayOfMonth(from).equals(formatDayOfMonth(to))) {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDayOfMonth(from);
            } else {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDayOfMonth(from) + RECURRING_DATE_SEPARATOR + TO
                                 + RECURRING_DATE_SEPARATOR + formatDayOfMonth(to);
            }
            break;
        case YEARLY:
            formattedResult += YEARLY;
            if (formatDateOfYear(from).equals(formatDateOfYear(to))) {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDateOfYear(from);
            } else {
                formattedResult += RECURRING_DATE_SEPARATOR + formatDateOfYear(from) + RECURRING_DATE_SEPARATOR + TO
                                 + RECURRING_DATE_SEPARATOR + formatDateOfYear(to);
            }
            break;
        default:
            // won't happen
        }
        if (recurringEndDateTime != null) {
            formattedResult += RECURRING_DATE_SEPARATOR + UNTIL
                    + RECURRING_DATE_SEPARATOR + formatDate(recurringEndDateTime);
        }
        return formattedResult;
    }

    public static String formatDayOfWeek(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DAY_OF_WEEK);
        return dateTime.format(formatter);
    }

    public static String formatDayOfMonth(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DAY_OF_MONTH);
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime + getDayNumberSuffix(Integer.valueOf(formattedDateTime));
    }

    public static String formatDateOfYear(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_OF_YEAR);
        return dateTime.format(formatter);
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

    private static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return SUFFIX_FOURTH_ONWARDS;
        }
        switch (day % 10) {
        case 1:
            return SUFFIX_FIRST;
        case 2:
            return SUFFIX_SECOND;
        case 3:
            return SUFFIX_THIRD;
        default:
            return SUFFIX_FOURTH_ONWARDS;
        }
    }
}
