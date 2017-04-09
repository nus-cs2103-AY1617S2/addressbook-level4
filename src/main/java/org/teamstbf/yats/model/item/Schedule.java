package org.teamstbf.yats.model.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {

	public static final String MESSAGE_TIME_ERROR = "Invalid or empty date/time entry";
	public static final String STRING_EMPTY = "";
	public static final SimpleDateFormat FORMATTER_TIME = new SimpleDateFormat("hh:mma ");
	private static final SimpleDateFormat FORMATTER_DATE = new SimpleDateFormat("hh:mma dd/MM/yyyy");
	public static final String MESSAGE_TIME_CONSTRAINTS = "non valid time";

	public static final String TIME_VALIDATION_REGEX = "\\b((1[0-2]|0?[1-9]):([0-5][0-9])([AP][M]))";
	public static final String MONTH_VALIDATION_REGEX = ".*(01|02|03|04|05|06|07|08|09|10|11|12).*";

	private Date scheduleDate;

	// @@author A0116219L
	/*
	 * Creates a Schedule object from the Date object given. Date can be null.
	 */
	public Schedule(Date dateObject) {
		this.scheduleDate = dateObject;
	}

	public String getTimeOnlyString() {
		return FORMATTER_TIME.format(scheduleDate);
	}

	public Schedule(String timeString) {
		validateDate(timeString);
		try {
			this.scheduleDate = STRING_EMPTY.equals(timeString) ? null : FORMATTER_DATE.parse(timeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Schedule // instanceof handles nulls
						&& this.toString().equals(((Schedule) other).toString())); // state
		// check
	}

	/*
	 * public SimpleDate getDate() { return this.date; } public Timing getTime()
	 * { return this.time; } public String getValue() { return this.value; }
	 */

	@Override
	public int hashCode() {
		return this.scheduleDate.hashCode();
	}

	/*
	 * public void setDate(SimpleDate endTime) { this.date = endTime; } public
	 * void setTime(Timing startTime) { this.time = startTime; }
	 */

	@Override
	public String toString() {
		if (this.scheduleDate == null) {
			return STRING_EMPTY;
		} else if (this.scheduleDate.equals("")) {
			return STRING_EMPTY;
		}

		String dateString = FORMATTER_DATE.format(this.scheduleDate);
		return dateString;
	}

	public Date getDate() {
		return this.scheduleDate;
	}

	public static boolean isValidSchedule(String timeDate) {
		String[] date = timeDate.split("\\s+");
		/*if (date.length != 2 ) {
			return false;
		}*/
		return (date[0].trim().matches(TIME_VALIDATION_REGEX) && validateDate(date[1]));
	}

	public static boolean validateDate(String date) {

		String[] splitDate = date.split("/");
		if (splitDate.length != 3 || splitDate[0].trim().length() != 2 || splitDate[1].trim().length() != 2 || splitDate[2].trim().length() != 4) {
			return false;
		}

		int day = Integer.parseInt(splitDate[0]);
		String month = splitDate[1];
		int year = Integer.parseInt(splitDate[2]);

		if (!month.matches(MONTH_VALIDATION_REGEX)) {
			return false;
		}

		if (day > 31) {
			return false;
		}

		if (day > 30 && (month.equals("11") || month.equals("04") || month .equals("06") || month.equals("09"))) {
			return false; // only 1, 3, 5, 7, 8, 10, 12 have 31 days
		} else if (month.equals("02")) {
			if (year % 4 == 0) {
				if (day > 29) {
					return false;
				} else {
					return true;
				}
			} else {
				if (day > 28) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			return true;
		}

	}
}
