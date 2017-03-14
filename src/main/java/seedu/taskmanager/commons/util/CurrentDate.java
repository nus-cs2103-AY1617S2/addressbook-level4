package seedu.taskmanager.commons.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

public class CurrentDate {

	public static final String MESSAGE_DAY_CONSTRAINTS = "Task date should be either a day (e.g. thursday) or a date with the format: DD/MM/YY (e.g. 03/03/17)";

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

	public CurrentDate() {
		currentDay = getCurrentDay();
		currentDate = getCurrentDate();
	}

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
				|| test.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY4);
	}

	public static int getNewDay(String day) {
		int newday;

		if (day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY2)
				|| day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY3)
				|| day.matches(CURRENTDATE_VALIDATION_REGEX_SUNDAY4)) {
			newday = 1;
			return newday;
		} else {
			if (day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY1) || day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY2)
					|| day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY3)
					|| day.matches(CURRENTDATE_VALIDATION_REGEX_MONDAY4)) {
				newday = 2;
				return newday;
			} else {
				if (day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY1)
						|| day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY2)
						|| day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY3)
						|| day.matches(CURRENTDATE_VALIDATION_REGEX_TUESDAY4)) {
					newday = 3;
					return newday;
				} else {
					if (day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY1)
							|| day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY2)
							|| day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY3)
							|| day.matches(CURRENTDATE_VALIDATION_REGEX_WEDNESDAY4)) {
						newday = 4;
						return newday;
					} else {
						if (day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY1)
								|| day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY2)
								|| day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY3)
								|| day.matches(CURRENTDATE_VALIDATION_REGEX_THURSDAY4)) {
							newday = 5;
							return newday;
						} else {
							if (day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY1)
									|| day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY2)
									|| day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY3)
									|| day.matches(CURRENTDATE_VALIDATION_REGEX_FRIDAY4)) {
								newday = 6;
								return newday;
							} else {
								newday = 7;
								return newday;
							}
						}
					}
				}
			}
		}

	}

	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// getTime() returns the current date in default time zone
		Date date = calendar.getTime();
		int day = calendar.get(Calendar.DATE);
		// Note: +1 the month for current month
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

		return day;
	}

	public static String getCurrentDate() {
		String newdate = "";
		String stringDay = "";
		String stringMonth = "";
		String stringYear = "";

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// getTime() returns the current date in default time zone
		Date date = calendar.getTime();
		int day = calendar.get(Calendar.DATE);
		// Note: +1 the month for current month
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

		if (day < 10) {
			stringDay = "0" + Integer.toString(day);
		} else
			stringDay = Integer.toString(day);

		if (month < 10) {
			stringMonth = "0" + Integer.toString(month);
		} else
			stringMonth = Integer.toString(month);

		stringYear = Integer.toString(year).substring(Math.max(Integer.toString(year).length() - 2, 0));

		newdate = stringDay + "/" + stringMonth + "/" + stringYear;

		return newdate;
	}

	public static String getNewDate(String givenDay) throws IllegalValueException {

		if (!isValidDay(givenDay)) {
			throw new IllegalValueException(MESSAGE_DAY_CONSTRAINTS);
		}

		int inputDay = getNewDay(givenDay);

		String updatedDate = "";
		String stringDay = "";
		String stringMonth = "";
		String stringYear = "";

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// getTime() returns the current date in default time zone
		Date date = calendar.getTime();
		int day = calendar.get(Calendar.DATE);
		// Note: +1 the month for current month
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

		int diffInDays = dayOfWeek - inputDay;

		if (diffInDays == 0)
			return getCurrentDate();
		if (diffInDays > 0)
			day += (7 - diffInDays);
		if (diffInDays < 0)
			day -= diffInDays;

		LocalDate testdate = LocalDate.of(year, month, day);
		int testdays = testdate.lengthOfMonth();

		if (day > testdays) {
			month += 1;
			day -= testdays;
		}

		if (month > 12) {
			month = 1;
			year += 1;
		}

		if (day < 10) {
			stringDay = "0" + Integer.toString(day);
		} else
			stringDay = Integer.toString(day);

		if (month < 10) {
			stringMonth = "0" + Integer.toString(month);
		} else
			stringMonth = Integer.toString(month);

		stringYear = Integer.toString(year).substring(Math.max(Integer.toString(year).length() - 2, 0));

		return updatedDate = stringDay + "/" + stringMonth + "/" + stringYear;
	}
}
