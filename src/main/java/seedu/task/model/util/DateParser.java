//@@evanyeung A0163744B
package seedu.task.model.util;

import java.util.Calendar;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateParser {
    public static final String DATE_STRING_ILLEGAL_FORMAT =
            "String must be of the form YYYY/MM/DD HHMM";
    public static final String DATE_ILLEGAL_DATE = "The given date is not valid";

    private static final int DEFAULT_SECONDS = 0;
    private static final int DEFAULT_MILLISECONDS = 0;
    private static final String DATE_STRING_VALIDATION_REGEX = "[0-9]{4}/[0-1][0-9]/[0-3][0-9] [0-2][0-9][0-5][0-9]";
    private static final String EMPTY_DATE_STRING = "";
    private static final int MONTH_OFFSET = 1;

    public static Calendar parse(String date) throws IllegalValueException {
        if (!isValidDateString(date)) {
            throw new IllegalValueException(DATE_STRING_ILLEGAL_FORMAT);
        }
        Calendar cal = Calendar.getInstance();
        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);
        int hour = getHour(date);
        int minute = getMinute(date);
        if(!isValidDate(year, month, day, hour, minute)) {
            throw new IllegalValueException(DATE_ILLEGAL_DATE);
        }
        cal.set(year, month, day, hour, minute, DEFAULT_SECONDS);
        cal.set(Calendar.MILLISECOND, DEFAULT_MILLISECONDS);
        return cal;
    }
    //@@evanyeung

    public static String toString(Calendar date) {
        String dateString;

        if (date == null) {
            return EMPTY_DATE_STRING;
        }

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        dateString = String.format("%4d/%02d/%02d %02d%02d", year, month + MONTH_OFFSET, day, hour, minute);

        return dateString;
    }

    //@@evanyeung A0163744B
    public static boolean isValidDateString(String test) {
        return test.matches(DATE_STRING_VALIDATION_REGEX);
    }

    private static boolean isValidDate(int year, int month, int day, int hour, int minute) {
        if (year < 0 || month < 0 || month > 11) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        if (day < 1 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return false;
        }
        return true;
    }

    private static int getYear(String date) {
        return Integer.parseInt(date.substring(0,  4));
    }

    private static int getMonth(String date) {
        return Integer.parseInt(date.substring(5,  7)) - MONTH_OFFSET;
    }

    private static int getDay(String date) {
        return Integer.parseInt(date.substring(8, 10));
    }

    private static int getHour(String date) {
        return Integer.parseInt(date.substring(11, 13));
    }

    private static int getMinute(String date) {
        return Integer.parseInt(date.substring(13));
    }
}
