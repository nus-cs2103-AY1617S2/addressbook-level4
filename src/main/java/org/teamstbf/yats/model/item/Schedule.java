package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

public class Schedule {

	public static final String MESSAGE_TIME_CONSTRAINTS = "cannot include 2 or more commas in time/date entry";
	private Timing time;
	private Date date;
	public String value;

	/**
	 * Represents an Event schedule in TaskManager. Values cannot be null
	 *
	 * @param startTime
	 * @param endTime
	 * @param deadline
	 * @throws IllegalValueException
	 */
	public Schedule(String timetoparse) throws IllegalValueException {
		String[] stringArray = timetoparse.split(",");
		if (stringArray.length > 2 || stringArray.length == 0) {
			throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
		}
		if (stringArray.equals(" ")) {
			this.time = new Timing(" ");
			this.date = new Date(" ");
		}
		if (stringArray.length == 1) {
			if (stringArray[0].contains("m")) {
				this.time = new Timing(stringArray[0]);
				this.date = new Date(" ");
			} else {
				this.time = new Timing(" ");
				this.date = new Date(stringArray[0]);
			}
			this.value = stringArray[0];
		} else {
			this.time = new Timing(stringArray[0]);
			this.date = new Date(stringArray[1]);
			this.value = timetoparse;
		}
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Schedule // instanceof handles nulls
						&& this.time.value.equals(((Schedule) other).time.value)
						&& this.date.value.equals(((Schedule) other).date.value)); // state
																					// check
	}

	public Date getDate() {
		return this.date;
	}

	public Timing getTime() {
		return this.time;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return time.hashCode();
	}

	public void setDate(Date endTime) {
		this.date = endTime;
	}

	public void setTime(Timing startTime) {
		this.time = startTime;
	}

	@Override
	public String toString() {
		return this.time.value + this.date.value;
	}

}
