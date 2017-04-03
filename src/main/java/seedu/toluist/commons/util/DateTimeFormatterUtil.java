//@@author A0131125Y
package seedu.toluist.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.toluist.model.Task.RecurringFrequency;

/**
 * A class to assist in formatting date
 */
public class DateTimeFormatterUtil {
    public static final String EVERY = "Every";
    public static final String OF_THE = "of the";
    public static final String TO = "to";
    public static final String UNTIL = "until";
    public static final String TASK_DEADLINE = "by ";
    public static final String EVENT_TO = " to ";
    public static final String YESTERDAY = "yesterday";
    public static final String TODAY = "today";
    public static final String TOMORROW = "tomorrow";
    public static final String DAY = "day";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String FORMAT_DAY_OF_WEEK = "EEEE";
    public static final String FORMAT_DAY_OF_MONTH = "d";
    public static final String FORMAT_MONTH_OF_YEAR = "MMMM";
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

    //@@author A0127545A
    /**
     * Format recurring floating task
     */
    public static String formatRecurringFloatingTask(LocalDateTime recurringEndDateTime,
            RecurringFrequency recurringFrequency) {
        String formattedResult = "";
        switch (recurringFrequency) {
        case DAILY:
            formattedResult += String.join(" ", EVERY, DAY);
            break;
        case WEEKLY:
            formattedResult += String.join(" ", EVERY, WEEK);
            break;
        case MONTHLY:
            formattedResult += String.join(" ", EVERY, MONTH);
            break;
        case YEARLY:
            formattedResult += String.join(" ", EVERY, YEAR);
            break;
        default:
            // won't happen
        }
        if (recurringEndDateTime != null) {
            formattedResult += RECURRING_DATE_SEPARATOR + String.join(" ", UNTIL, formatDate(recurringEndDateTime));
        }
        return formattedResult;
    }

    /**
     * Format recurring task with deadline
     */
    public static String formatRecurringTaskDeadline(LocalDateTime deadline, LocalDateTime recurringEndDateTime,
            RecurringFrequency recurringFrequency) {
        String formattedResult = "";
        switch (recurringFrequency) {
        case DAILY:
            formattedResult += String.join(" ", EVERY, formatTime(deadline), OF_THE, DAY);
            break;
        case WEEKLY:
            formattedResult += String.join(" ", EVERY, formatDayOfWeek(deadline), OF_THE, WEEK);
            break;
        case MONTHLY:
            formattedResult += String.join(" ", EVERY, formatDayOfMonth(deadline), OF_THE, MONTH);
            break;
        case YEARLY:
            formattedResult += String.join(" ", EVERY, formatDateOfYear(deadline), OF_THE, YEAR);
            break;
        default:
            // won't happen
        }
        if (recurringEndDateTime != null) {
            formattedResult += RECURRING_DATE_SEPARATOR + String.join(" ", UNTIL, formatDate(recurringEndDateTime));
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
            if (formatTime(from).equals(formatTime(to))) {
                formattedResult += String.join(" ", EVERY, formatTime(from), OF_THE, DAY);
            } else {
                formattedResult += String.join(" ", EVERY, formatTime(from), TO, formatTime(to), OF_THE, DAY);
            }
            break;
        case WEEKLY:
            if (formatDayOfWeek(from).equals(formatDayOfWeek(to))) {
                formattedResult += String.join(" ", EVERY, formatDayOfWeek(from), OF_THE, WEEK);
            } else {
                formattedResult += String.join(" ", EVERY, formatDayOfWeek(from),
                                                       TO, formatDayOfWeek(to), OF_THE, WEEK);
            }
            break;
        case MONTHLY:
            if (formatDayOfMonth(from).equals(formatDayOfMonth(to))) {
                formattedResult += String.join(" ", EVERY, formatDayOfMonth(from), OF_THE, MONTH);
            } else {
                formattedResult += String.join(" ", EVERY, formatDayOfMonth(from),
                                                       TO, formatDayOfMonth(to), OF_THE, MONTH);
            }
            break;
        case YEARLY:
            if (formatDateOfYear(from).equals(formatDateOfYear(to))) {
                formattedResult += String.join(" ", EVERY, formatDateOfYear(from), OF_THE, YEAR);
            } else {
                formattedResult += String.join(" ", EVERY, formatDateOfYear(from),
                                                       TO, formatDateOfYear(to), OF_THE, YEAR);
            }
            break;
        default:
            // won't happen
        }
        if (recurringEndDateTime != null) {
            formattedResult += RECURRING_DATE_SEPARATOR + String.join(" ", UNTIL, formatDate(recurringEndDateTime));
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

    //@@author A0131125Y
    public static String formatDateOfYear(LocalDateTime dateTime) {
        String formattedDay = formatDayOfMonth(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_MONTH_OF_YEAR);
        String formattedMonth = dateTime.format(formatter);
        return String.join(" ", formattedDay, formattedMonth);
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

    //@@author A0127545A
    /**
     * Gets the suffix for a given day number
     * e.g.  3 -> rd
     *      12 -> th
     *      15 -> th
     *      21 -> st
     */
    public static String getDayNumberSuffix(int day) {
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
