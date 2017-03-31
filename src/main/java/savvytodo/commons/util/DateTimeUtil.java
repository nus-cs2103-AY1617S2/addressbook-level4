package savvytodo.commons.util;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.task.DateTime;

//@@author A0140016B
/**
 * @author A0140016B
 * Utility methods related to DateTime
 */
public class DateTimeUtil {

    private static final String TIME_ONLY_FORMAT = "HHmm";
    private static final String DATE_ONLY_FORMAT = "dd/MM/uuuu";
    private static final String DATE_FORMAT = "d/M/uuuu HHmm";
    private static final String DATE_STRING_FORMAT = "dd/MM/uuuu HHmm";

    public static final int FIRST_HOUR_OF_DAY = 0;
    public static final int FIRST_MINUTE_OF_DAY = 0;
    public static final int FIRST_SECOND_OF_DAY = 0;
    public static final int LAST_HOUR_OF_DAY = 23;
    public static final int LAST_MINUTE_OF_DAY = 59;
    public static final int LAST_SECOND_OF_DAY = 59;

    private static final String DAILY = "daily";
    private static final String WEEKLY = "weekly";
    private static final String MONTHLY = "monthly";
    private static final String YEARLY = "yearly";
    private static final int INCREMENT_FREQ = 1;

    private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern(DATE_ONLY_FORMAT);
    private static final DateTimeFormatter TIME_ONLY_FORMATTER = DateTimeFormatter.ofPattern(TIME_ONLY_FORMAT);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT)
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_STRING_FORMATTER = DateTimeFormatter.ofPattern(DATE_STRING_FORMAT);

    public static final String MESSAGE_INCORRECT_SYNTAX = "It must be a valid date";

    private static final String MESSAGE_DURATION = "%1$s hr %2$s min";

    public static final String MESSAGE_FREE_TIME_SLOT = StringUtil.SYSTEM_NEWLINE + "%1$s. %2$shrs to %3$shrs (%4$s)";


    /**
     * Extracts the new task's dateTime from the string arguments.
     * @return String[] with first index being the startDate time and second index being the end
     *         date time
     */
    public static String[] parseStringToDateTime(String dateTimeArgs) {
        return NattyDateTimeParserUtil.parseStringToDateTime(dateTimeArgs);
    }

    /**
     * Extracts the new task's dateTime from the string arguments.
     * @return LocalDateTime
     */
    private static LocalDateTime parseStringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_FORMATTER);
    }

    /**
     * Checks if given endDateTime is within today and the end of this week
     */
    public static boolean isWithinWeek(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endThisWeek = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0)
                    .withSecond(0);
            return endDateTime.isAfter(now) && endDateTime.isBefore(endThisWeek);
        }
    }

    /**
     * Checks if given endDateTime is before the end of today
     */
    public static boolean isOverDue(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            return endDateTime.isBefore(now);
        }
    }

    /**
     * Checks whether the dateTimeQuery falls within the range of the dateTimeSource i.e.
     * dateTimeQuery startDateTime is equals to or before the dateTimeSource endDateTime &&
     * dateTimeQuery endDateTime is equals to or after the dateTimeSource startDateTime
     * Return false if task is a floating task (i.e. no start or end dateTime)
     * @param dateTimeSource
     * @param dateTimeQuery
     * @throws IllegalValueException
     * @throws DateTimeException
     */
    public static boolean isDateTimeWithinRange(DateTime dateTimeSource, DateTime dateTimeQuery)
            throws DateTimeException, IllegalValueException {
        if (dateTimeSource.endValue == null) {
            return false;
        }

        DateTime dateTime1 = fillDateTime(dateTimeSource);
        DateTime dateTime2 = fillDateTime(dateTimeQuery);

        return !parseStringToLocalDateTime(dateTime1.endValue)
                .isBefore(parseStringToLocalDateTime(dateTime2.startValue))
                && !parseStringToLocalDateTime(dateTime1.startValue)
                        .isAfter(parseStringToLocalDateTime(dateTime2.endValue));
    }

    /**
     * Checks whether the dateTimeQuery conflicts with the dateTimeSource i.e. dateTimeQuery
     * endDateTime occurs after the dateTimeSource startDateTime && dateTimeQuery startDateTime
     * occurs before the dateTimeSource endDateTime
     * Return false if task is a floating task (i.e. no start or end dateTime)
     * @throws IllegalValueException
     * @throws DateTimeException
     */
    public static boolean isDateTimeConflict(DateTime dateTimeSource, DateTime dateTimeQuery)
            throws DateTimeException, IllegalValueException {
        if (dateTimeSource.startValue.equalsIgnoreCase(StringUtil.EMPTY_STRING)
                || dateTimeSource.endValue.equalsIgnoreCase(StringUtil.EMPTY_STRING)) {
            return false;
        }

        if (dateTimeQuery.startValue.equalsIgnoreCase(StringUtil.EMPTY_STRING)
                || dateTimeQuery.endValue.equalsIgnoreCase(StringUtil.EMPTY_STRING)) {
            return false;
        }

        DateTime dateTime1 = fillDateTime(dateTimeSource);
        DateTime dateTime2 = fillDateTime(dateTimeQuery);

        return parseStringToLocalDateTime(dateTime1.endValue).isAfter(parseStringToLocalDateTime(dateTime2.startValue))
                && parseStringToLocalDateTime(dateTime1.startValue)
                        .isBefore(parseStringToLocalDateTime(dateTime2.endValue));
    }

    private static DateTime fillDateTime(DateTime filledDateTime) throws IllegalValueException {
        DateTime dateTimeToFill = filledDateTime;

        dateTimeToFill.setEnd(parseStringToLocalDateTime(filledDateTime.endValue));
        dateTimeToFill.setStart(parseStringToLocalDateTime(filledDateTime.startValue));

        return dateTimeToFill;
    }


    /**
     * Returns an arraylist of free datetime slots in a specified date
     */
    public static ArrayList<DateTime> getListOfFreeTimeSlotsInDate(
            DateTime dateToCheck,
            ArrayList<DateTime> listOfFilledTimeSlotsInDate) {
        ArrayList<DateTime> listOfFreeTimeSlots = new ArrayList<DateTime>();
        LocalDateTime startDateTime = parseStringToLocalDateTime(dateToCheck.startValue);
        LocalDateTime endDateTime;

        for (DateTime dt : listOfFilledTimeSlotsInDate) {
            if (dt.startValue == null) {
                continue;
            } else {
                endDateTime = parseStringToLocalDateTime(dt.startValue);
            }

            if (startDateTime.isBefore(endDateTime)) {
                listOfFreeTimeSlots
                        .add(new DateTime(startDateTime, endDateTime));
            }

            if (startDateTime.isBefore(parseStringToLocalDateTime(dt.endValue))) {
                startDateTime = parseStringToLocalDateTime(dt.endValue);
            }
        }

        if (startDateTime.isBefore(parseStringToLocalDateTime(dateToCheck.endValue))) {
            listOfFreeTimeSlots
                    .add(new DateTime(startDateTime, parseStringToLocalDateTime(dateToCheck.endValue)));
        }

        return listOfFreeTimeSlots;
    }


    public static String getDayAndDateString(DateTime dateTime) {
        StringBuilder sb = new StringBuilder();

        sb.append(parseStringToLocalDateTime(dateTime.endValue).getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)).append(",")
                .append(parseStringToLocalDateTime(dateTime.endValue).format(DATE_ONLY_FORMATTER));

        return sb.toString();
    }


    public static String getStringOfFreeDateTimeInDate(DateTime dateToCheck,
            ArrayList<DateTime> listOfFreeTimeSlotsInDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(getDayAndDateString(dateToCheck))
                .append(":");

        int counter = 1;

        for (DateTime dt : listOfFreeTimeSlotsInDate) {
            sb.append(String.format(MESSAGE_FREE_TIME_SLOT, counter,
                    parseStringToLocalDateTime(dt.startValue).format(TIME_ONLY_FORMATTER),
                    parseStringToLocalDateTime(dt.endValue).format(TIME_ONLY_FORMATTER),
                    getDurationBetweenTwoLocalDateTime(parseStringToLocalDateTime(dt.startValue),
                            parseStringToLocalDateTime(dt.endValue))));
            counter++;
        }

        return sb.toString();
    }

    public static String getDurationBetweenTwoLocalDateTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format(MESSAGE_DURATION, hours, minutes);
    }

    /**
     * Modify the date based on the new hour, min and sec
     */
    public static Date setDateTime(Date toBeEdit, int hour, int min, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toBeEdit);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        toBeEdit = calendar.getTime();

        return toBeEdit;
    }

    /**
     * @param recurDate usually is the start date of an event
     * Modifies the recurDate based on the frequency for recurring tasks.
     * freqType cannot be null or None
     */
    public static String getRecurDate(String recurDate, String freqType) {
        LocalDateTime date = LocalDateTime.parse(recurDate, DATE_FORMATTER);

        switch (freqType.toLowerCase()) {
        case DAILY:
            date = date.plusDays(INCREMENT_FREQ);
            break;
        case WEEKLY:
            date = date.plusWeeks(INCREMENT_FREQ);
            break;
        case MONTHLY:
            date = date.plusMonths(INCREMENT_FREQ);
            break;
        case YEARLY:
            date = date.plusYears(INCREMENT_FREQ);
            break;
        }

        recurDate = date.format(DATE_STRING_FORMATTER);
        return recurDate;
    }

    public static LocalDateTime setLocalTime(LocalDateTime dateTime, int hour, int min, int sec) {
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), hour, min, sec);
    }
}
//@@author A0140016B
