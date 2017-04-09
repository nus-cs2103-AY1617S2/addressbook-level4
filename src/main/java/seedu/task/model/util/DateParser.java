//@@author A0163744B
package seedu.task.model.util;

import java.util.Calendar;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * A utility class that hold functions to deal with date strings. This includes parsing
 * different formats of input date strings to {@code Calendar} objects and String representations
 * for {@code Calendar} dates.
 */
public class DateParser {
    public static final String DATE_STRING_ILLEGAL_FORMAT =
            "String must be of the form DD/MM/YYYY HHMM, or day_of_week|today|tomorrow HHMM";
    public static final String DATE_ILLEGAL_DATE = "The given date is not valid";

    private static final int DEFAULT_SECONDS = 0;
    private static final int DEFAULT_MILLISECONDS = 0;
    private static final String DATE_STRING_VALIDATION_REGEX =
            "^([A-Za-z]{3,9}|[0-3][0-9]/[0-1][0-9]/[0-9]{4}|[0-3][0-9]/[0-1][0-9])( [0-2][0-9][0-5][0-9]|)$";
    private static final String EMPTY_DATE_STRING = "";
    private static final int MONTH_OFFSET = 1;

    private static final int MONTHS_PER_YEAR = 12;
    private static final int DAYS_PER_WEEK = 7;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINIMUM_YEAR = 0;
    private static final int MINIMUM_MONTH = 0;
    private static final int MINIMUM_DAY = 1;
    private static final int MINIMUM_HOUR = 0;
    private static final int MINIMUM_MINUTE = 0;

    //@@author A0164103W
    private static int parsedDate[] = new int[3];
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int INDEX_SUN = 1;
    private static final int INDEX_MON = 2;
    private static final int INDEX_TUE = 3;
    private static final int INDEX_WED = 4;
    private static final int INDEX_THU = 5;
    private static final int INDEX_FRI = 6;
    private static final int INDEX_SAT = 7;

    //@@author A0163744B
    public static Calendar parse(String date) throws IllegalValueException {
        if (!isValidDateString(date)) {
            throw new IllegalValueException(DATE_STRING_ILLEGAL_FORMAT);
        }
        Calendar cal = Calendar.getInstance();
        int year = getDate(date)[INDEX_YEAR];
        int month = getDate(date)[INDEX_MONTH];
        int day = getDate(date)[INDEX_DAY];
        int hour = getHour(date);
        int minute = getMinute(date);
        if (!isValidDate(year, month, day, hour, minute)) {
            throw new IllegalValueException(DATE_ILLEGAL_DATE);
        }
        cal.set(year, month, day, hour, minute, DEFAULT_SECONDS);
        cal.set(Calendar.MILLISECOND, DEFAULT_MILLISECONDS);
        return cal;
    }

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

        dateString = String.format("%02d/%02d/%4d %02d%02d", day, month + MONTH_OFFSET, year, hour, minute);

        return dateString;
    }

    public static boolean isValidDateString(String test) {
        return test.matches(DATE_STRING_VALIDATION_REGEX);
    }

    public static boolean isValidDate(int year, int month, int day, int hour, int minute) {
        if (year < MINIMUM_YEAR || month < MINIMUM_MONTH || month >= MONTHS_PER_YEAR) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        if (day < MINIMUM_DAY || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (hour < MINIMUM_HOUR || hour >= HOURS_PER_DAY || minute < MINIMUM_MINUTE || minute >= MINUTES_PER_HOUR) {
            return false;
        }
        return true;
    }

    //@@author A0164103W
    /**
     * Returns date in integers given string format of date
     *
     * @param date in string format
     * @return array containing day, month, and year at INDEX_DAY, INDEX_MONTH, INDEX_YEAR respectively
     * @throws IllegalValueException if illegal values are entered
     */
    private static int[] getDate(String date) throws IllegalValueException {
        Calendar calDate = Calendar.getInstance();
        int dayOfWeek = calDate.get(Calendar.DAY_OF_WEEK);
        String firstWord;
        if (date.indexOf(" ") == -1) {
            firstWord = date;
        } else {
            firstWord = date.substring(0, date.indexOf(" "));
        }

        if (date.substring(0, 1).matches("[0-9]")) { //Date given in number format
            parsedDate[INDEX_DAY] = Integer.parseInt(date.substring(0, 2));
            parsedDate[INDEX_MONTH] = Integer.parseInt(date.substring(3, 5)) - MONTH_OFFSET;

            int l = firstWord.length();
            if (l == 5) { //No year given
                parsedDate[INDEX_YEAR] = calDate.get(Calendar.YEAR);
            } else if (l == 10) {
                parsedDate[INDEX_YEAR] = Integer.parseInt(date.substring(6, 10));
            } else {
                throw new IllegalValueException(DATE_STRING_ILLEGAL_FORMAT);
            }
        } else {
            switch (firstWord.toLowerCase()) { //Date given in recognized keyword
            case "today" : {
                break;
            }
            case "tomorrow" : {
                calDate.add(Calendar.DATE, 1);
                break;
            }
            case "monday" :
            case "mon": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_MON));
                break;
            }
            case "tuesday" :
            case "tues" :
            case "tue": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_TUE));
                break;
            }
            case "wednesday" :
            case "wed": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_WED));
                break;
            }
            case "thursday" :
            case "thurs" :
            case "thu": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_THU));
                break;
            }
            case "friday" :
            case "fri": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_FRI));
                break;
            }
            case "saturday" :
            case "sat": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_SAT));
                break;
            }
            case "sunday" :
            case "sun": {
                calDate.add(Calendar.DATE, compareDayOfWeek(dayOfWeek, INDEX_SUN));
                break;
            }
            default:
                throw new IllegalValueException(DATE_STRING_ILLEGAL_FORMAT);
            }
            parsedDate[INDEX_DAY] = calDate.get(Calendar.DAY_OF_MONTH);
            parsedDate[INDEX_MONTH] = calDate.get(Calendar.MONTH);
            parsedDate[INDEX_YEAR] = calDate.get(Calendar.YEAR);
        }

        return parsedDate;
    }

    private static int compareDayOfWeek(int day1, int day2) {
        return day2 - day1 < 0 ?
                day2 - day1 + DAYS_PER_WEEK :
                day2 - day1;
    }

    private static int getHour(String date) {
        if (date.indexOf(" ") == -1) {
            return 0;
        } else {
            String time = date.substring(date.indexOf(" ") + 1);
            return Integer.parseInt(time.substring(0, 2));
        }
    }

    private static int getMinute(String date) {
        if (date.indexOf(" ") == -1) {
            return 0;
        } else {
            String time = date.substring(date.indexOf(" ") + 1);
            return Integer.parseInt(time.substring(3));
        }
    }
}
