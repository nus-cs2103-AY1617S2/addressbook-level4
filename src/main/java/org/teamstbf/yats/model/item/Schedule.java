package org.teamstbf.yats.model.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {

	public static final String MESSAGE_TIME_ERROR = "Invalid or empty date/time entry";
	public static final String STRING_EMPTY = "";

	private static final SimpleDateFormat FORMATTER_DATE = new SimpleDateFormat("hh:mma dd/MM/yyyy");
	public static final String MESSAGE_TIME_CONSTRAINTS = "non valid time";
	// public static final String SCHEDULE_VALIDATION_REGEX = "((1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(AM|PM)|^(?![\\s\\S])) (^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$|^(?![\\s\\S]))";

	private Date scheduleDate;

	// @@author A0116219L
	/*
	 * Creates a Schedule object from the Date object given. Date can be null.
	 */
	public Schedule(Date dateObject) {
		this.scheduleDate = dateObject;
	}

	public Schedule(String timeString) {
		// CHANGE THIS
		try {
			this.scheduleDate = STRING_EMPTY.equals(timeString) ? null : FORMATTER_DATE.parse(timeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTimeOnlyString() {
	    return FORMATTER_TIME.format(scheduleDate);
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

	/* public static boolean isValidSchedule(String date) {

		return date.matches(SCHEDULE_VALIDATION_REGEX);
	} */

}
