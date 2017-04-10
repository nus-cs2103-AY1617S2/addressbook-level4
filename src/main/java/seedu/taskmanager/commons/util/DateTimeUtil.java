package seedu.taskmanager.commons.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.TimeZone;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

// @@author A0142418L
public class DateTimeUtil {

    public static final String MESSAGE_DAY_CONSTRAINTS = "Task date should be either "
            + "a day (e.g. thursday) or a date with the format: DD/MM/YY (e.g. 03/03/17)\n"
            + "May also include time (e.g. 1400) behind date \n"
            + "Enter HELP for user guide with detailed explanations of all commands";

    public static final String CURRENTDATE_VALIDATION_REGEX_TODAY1 = "Today";
    public static final String CURRENTDATE_VALIDATION_REGEX_TODAY2 = "today";
    public static final String CURRENTDATE_VALIDATION_REGEX_TOMORROW1 = "Tomorrow";
    public static final String CURRENTDATE_VALIDATION_REGEX_TOMORROW2 = "tomorrow";
    public static final String CURRENTDATE_VALIDATION_REGEX_TOMORROW3 = "Tmr";
    public static final String CURRENTDATE_VALIDATION_REGEX_TOMORROW4 = "tmr";
    public static final String CURRENTDATE_VALIDATION_REGEX_MONDAY1 = "Monday";
    public static final String CURRENTDATE_VALIDATION_REGEX_MONDAY2 = "monday";
    public static final String CURRENTDATE_VALIDATION_REGEX_MONDAY3 = "Mon";
    public static final String CURRENTDATE_VALIDATION_REGEX_MONDAY4 = "mon";
    public static final String CURRENTDATE_VALIDATION_REGEX_TUESDAY1 = "Tuesday";
    public static final String CURRENTDATE_VALIDATION_REGEX_TUESDAY2 = "tuesday";
    public static final String CURRENTDATE_VALIDATION_REGEX_TUESDAY3 = "Tues";
    public static final String CURRENTDATE_VALIDATION_REGEX_TUESDAY4 = "tues";
    public static final String CURRENTDATE_VALIDATION_REGEX_WEDNESDAY1 = "Wednesday";
    public static final String CURRENTDATE_VALIDATION_REGEX_WEDNESDAY2 = "wednesday";
    public static final String CURRENTDATE_VALIDATION_REGEX_WEDNESDAY3 = "Wed";
    public static final String CURRENTDATE_VALIDATION_REGEX_WEDNESDAY4 = "wed";
    public static final String CURRENTDATE_VALIDATION_REGEX_THURSDAY1 = "Thursday";
    public static final String CURRENTDATE_VALIDATION_REGEX_THURSDAY2 = "thursday";
    public static final String CURRENTDATE_VALIDATION_REGEX_THURSDAY3 = "Thurs";
    public static final String CURRENTDATE_VALIDATION_REGEX_THURSDAY4 = "thurs";
    public static final String CURRENTDATE_VALIDATION_REGEX_FRIDAY1 = "Friday";
    public static final String CURRENTDATE_VALIDATION_REGEX_FRIDAY2 = "friday";
    public static final String CURRENTDATE_VALIDATION_REGEX_FRIDAY3 = "Fri";
    public static final String CURRENTDATE_VALIDATION_REGEX_FRIDAY4 = "fri";
    public static final String CURRENTDATE_VALIDATION_REGEX_SATURDAY1 = "Saturday";
    public static final String CURRENTDATE_VALIDATION_REGEX_SATURDAY2 = "saturday";
    public static final String CURRENTDATE_VALIDATION_REGEX_SATURDAY3 = "Sat";
    public static final String CURRENTDATE_VALIDATION_REGEX_SATURDAY4 = "sat";
    public static final String CURRENTDATE_VALIDATION_REGEX_SUNDAY1 = "Sunday";
    public static final String CURRENTDATE_VALIDATION_REGEX_SUNDAY2 = "sunday";
    public static final String CURRENTDATE_VALIDATION_REGEX_SUNDAY3 = "Sun";
    public static final String CURRENTDATE_VALIDATION_REGEX_SUNDAY4 = "sun";

    public static int currentDay;
    public static String currentDate = "";

    public DateTimeUtil() {
        currentDay = getCurrentDay();
        currentDate = getCurrentDate();
    }

    /**
     * Checks to see if user has input a valid day, this function includes
     * different spellings of the same day
     */
    public static boolean isValidDay(String test) {
        return test.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY1) || test.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY4)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TODAY1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TODAY2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TOMORROW1)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TOMORROW2)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TOMORROW3)
                || test.matches(CURRENTDATE_VALIDATION_REGEX_TOMORROW4);
    }

    /**
     * @return Day number relative to the day itself (e.g. Sunday == 1 &
     *         Wednesday == 4)
     */
    public static int getNewDay(String day) {

        if (isSunday(day)) {
            return 1;
        } else {
            if (isMonday(day)) {
                return 2;
            } else {
                if (isTuesday(day)) {
                    return 3;
                } else {
                    if (isWednesday(day)) {
                        return 4;
                    } else {
                        if (isThursday(day)) {
                            return 5;
                        } else {
                            if (isFriday(day)) {
                                return 6;
                            } else {
                                if (isSaturday(day)) {
                                    return 7;
                                } else {
                                    if (isToday(day)) {
                                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                                        return calendar.get(Calendar.DAY_OF_WEEK);
                                    } else {
                                        return getTomorrowDay();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @return Current Day with respect to the date on the computer
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    /**
     * @return Current Date with respect to the date on the computer
     */
    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        // getTime() returns the current date in default time zone
        int day = calendar.get(Calendar.DATE);
        // Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return getFormattedDate(day, month, year);
    }

    /**
     * If user inputs a day, function will change the day given by user to the
     * actual date relative to the current date.
     *
     * @param givenDay
     * @return updatedDate in DD/MM/YY format
     * @throws IllegalValueException
     */
    public static String getNewDate(String givenDay) throws IllegalValueException {

        if (!isValidDay(givenDay)) {
            throw new IllegalValueException(MESSAGE_DAY_CONSTRAINTS);
        }

        int inputDay = getNewDay(givenDay);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        // getTime() returns the current date in default time zone
        int day = calendar.get(Calendar.DATE);
        // Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        LocalDate testdate = LocalDate.of(year, month, day);
        int testdays = testdate.lengthOfMonth();

        int diffInDays = dayOfWeek - inputDay;

        if (diffInDays == 0) {
            return getCurrentDate();
        }
        if (diffInDays > 0) {
            day += (7 - diffInDays);
        }
        if (diffInDays < 0) {
            day -= diffInDays;
        }

        if (day > testdays) {
            month += 1;
            day -= testdays;
        }
        if (month > 12) {
            month = 1;
            year += 1;
        }

        return getFormattedDate(day, month, year);
    }

    public static int isDateWithin(String date, String startDate, String endDate) {
        String[] dmy = date.trim().split("/");
        int day = Integer.parseInt(dmy[0]);
        int month = Integer.parseInt(dmy[1]);
        int year = Integer.parseInt(dmy[2]);

        String[] dmyStart = startDate.trim().split("/");
        int dayStart = Integer.parseInt(dmyStart[0]);
        int monthStart = Integer.parseInt(dmyStart[1]);
        int yearStart = Integer.parseInt(dmyStart[2]);

        String[] dmyEnd = endDate.trim().split("/");
        int dayEnd = Integer.parseInt(dmyEnd[0]);
        int monthEnd = Integer.parseInt(dmyEnd[1]);
        int yearEnd = Integer.parseInt(dmyEnd[2]);

        if (year < yearEnd && yearStart < year) {
            return 1;
        } else {
            if (year == yearEnd && year == yearStart) {
                if (month < monthEnd && monthStart < month) {
                    return 1;
                } else {
                    if (month == monthEnd && month == monthStart) {
                        if (day < dayEnd && dayStart < day) {
                            return 1;
                        } else {
                            if (day == dayEnd && dayStart == day) {
                                return 2;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    // @@author A0141102H

    public static boolean isValidDate(String date) {
        String[] dmy = date.trim().split("/");
        int day = Integer.parseInt(dmy[0]);
        int month = Integer.parseInt(dmy[1]);
        int year = Integer.parseInt(dmy[2]);

        YearMonth yearMonthObject = YearMonth.of(2000 + year, month);

        return !(day > yearMonthObject.lengthOfMonth() || month > 12);
    }

    public static String getFutureDate(int loops, String typeOfRecurrence, String existingDate) {
        String[] dmy = existingDate.trim().split("/");
        int day = Integer.parseInt(dmy[0]);
        int month = Integer.parseInt(dmy[1]);
        int year = Integer.parseInt(dmy[2]);

        String newDate = "";
        String newDay = "";
        String newMonth = "";

        YearMonth yearMonthObject = YearMonth.of(2000 + year, month);

        if (typeOfRecurrence.equalsIgnoreCase("days") || typeOfRecurrence.equalsIgnoreCase("day")) {
            day = day + loops;
            while (day > yearMonthObject.lengthOfMonth()) {
                day = day - yearMonthObject.lengthOfMonth();
                month = month + 01;
                if (month > 12) {
                    year = year + 1;
                    month = 01;
                }
                yearMonthObject = YearMonth.of(2000 + year, month);
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("weeks") || typeOfRecurrence.equalsIgnoreCase("week")) {
            day = day + loops * 7;
            while (day > yearMonthObject.lengthOfMonth()) {
                day = day - yearMonthObject.lengthOfMonth();
                month = month + 01;
                if (month > 12) {
                    year = year + 1;
                    month = 01;
                }
                yearMonthObject = YearMonth.of(2000 + year, month);
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("months") || typeOfRecurrence.equalsIgnoreCase("month")) {
            month = month + loops;
            while (month > 12) {
                month = month - 12;
                year = year + 1;
            }
        }

        if (typeOfRecurrence.equalsIgnoreCase("years") || typeOfRecurrence.equalsIgnoreCase("year")) {
            year = year + loops;
        }

        newDay = convertIntegerToTwoCharString(day);

        newMonth = convertIntegerToTwoCharString(month);

        newDate = newDay + "/" + newMonth + "/" + year;

        return newDate;
    }

    // @@author

    public static boolean isValidEventTimePeriod(String startDate, String startTime, String endDate, String endTime)
            throws IllegalValueException {
        String[] splitedStartDate = (startDate.trim()).split("/");
        String[] splitedEndDate = (endDate.trim()).split("/");
        boolean isValidStartEnd = true;
        if (Integer.parseInt(splitedEndDate[2]) <= Integer.parseInt(splitedStartDate[2])) {
            if (Integer.parseInt(splitedEndDate[2]) < Integer.parseInt(splitedStartDate[2])) {
                isValidStartEnd = false;
            } else {
                if (Integer.parseInt(splitedEndDate[1]) <= Integer.parseInt(splitedStartDate[1])) {
                    if (Integer.parseInt(splitedEndDate[0]) < Integer.parseInt(splitedStartDate[0])) {
                        isValidStartEnd = false;
                    } else {
                        if (Integer.parseInt(splitedEndDate[0]) <= Integer.parseInt(splitedStartDate[0])) {
                            if (Integer.parseInt(splitedEndDate[0]) < Integer.parseInt(splitedStartDate[0])) {
                                isValidStartEnd = false;
                            } else {
                                if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                                    throw new IllegalValueException(
                                            "Invalid input of time, start time has to be earlier than end time");
                                }
                            }
                        }
                    }
                }
            }
        }
        return isValidStartEnd;
    }

    public static boolean isValidTime(String value) {
        return (value.matches("\\d+") && (Integer.parseInt(value) < 2400) && (Integer.parseInt(value) >= 0)
                && (((Integer.parseInt(value)) % 100) < 60));
    }

    public static String includeOneHourBuffer(String startTime) {
        String endTime;
        endTime = Integer.toString(100 + Integer.parseInt(startTime));
        if (Integer.parseInt(endTime) < 1000) {
            endTime = "0" + endTime;
        }

        if (!isValidTime(endTime)) {
            endTime = Integer.toString(Integer.parseInt(endTime) - 2400);
            endTime = fourDigitTimeFormat(endTime);

        }
        return endTime;
    }

    public static String fourDigitTimeFormat(String endTime) {
        if (Integer.parseInt(endTime) >= 10) {
            StringBuilder stringBuilderTime = new StringBuilder();

            stringBuilderTime.append("00");
            stringBuilderTime.append(endTime);
            endTime = stringBuilderTime.toString();

        } else {
            StringBuilder stringBuilderTime = new StringBuilder();

            stringBuilderTime.append("000");
            stringBuilderTime.append(endTime);
            endTime = stringBuilderTime.toString();
        }
        return endTime;
    }

    // @@author A0142418L
    /**
     * @param dayOfWeek
     * @return Integer day value of tomorrow.
     */
    private static int getTomorrowDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek + 1 == 8) {
            return dayOfWeek = 1;
        } else {
            return dayOfWeek + 1;
        }
    }

    /**
     * @param day
     * @return true if input matches "Today" regex.
     */
    private static boolean isToday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_TODAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_TODAY2);
    }

    /**
     * @param day
     * @return true if input matches "Saturday" regex.
     */
    private static boolean isSaturday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY1)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_SATURDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Friday" regex.
     */
    private static boolean isFriday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Thursday" regex.
     */
    private static boolean isThursday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY1)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Wednesday" regex.
     */
    private static boolean isWednesday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY1)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Tuesday" regex.
     */
    private static boolean isTuesday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Monday" regex.
     */
    private static boolean isMonday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY4);
    }

    /**
     * @param day
     * @return true if input matches "Sunday" regex.
     */
    private static boolean isSunday(String day) {
        return day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY2)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY3)
                || day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY4);
    }

    /**
     * @param day
     * @return Integer as a 2 character string
     */
    private static String convertIntegerToTwoCharString(int integer) {
        String string;
        if (integer < 10) {
            string = "0" + Integer.toString(integer);
        } else {
            string = Integer.toString(integer);
        }
        return string;
    }

    /**
     * @param day
     * @param month
     * @param year
     * @return Date formatted in defined format for application
     */
    private static String getFormattedDate(int day, int month, int year) {
        String updatedDate;
        String stringDay;
        String stringMonth;
        String stringYear;
        stringDay = convertIntegerToTwoCharString(day);
        stringMonth = convertIntegerToTwoCharString(month);
        stringYear = Integer.toString(year).substring(Math.max(Integer.toString(year).length() - 2, 0));

        updatedDate = stringDay + "/" + stringMonth + "/" + stringYear;
        return updatedDate;
    }

}
