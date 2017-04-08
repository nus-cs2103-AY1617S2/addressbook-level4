package org.teamstbf.yats.model.item;

import java.util.ArrayList;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Periodicity in the Task Manager. Guarantees: immutable;
 * is valid as declared in {@link #isValidPeriod(String)}
 */

public class Periodic {

	private static boolean isPeriodic = false;
	public static final String MESSAGE_PERIODIC_CONSTRAINTS = "Periodicity must be none, daily, weekly or monthly";
	public static final String PERIODIC_VALIDATION_REGEX = ".*(none|daily|weekly|monthly).*";

	public static boolean isPeriodic() {
		return isPeriodic;
	}

	/**
	 * Returns if a given string is a valid period.
	 */
	public static boolean isValidPeriod(String test) {
		return test.matches(PERIODIC_VALIDATION_REGEX);
	}

	public static void setPeriodicityFalse() {
		isPeriodic = false;
	}

	public static void setPeriodicityTrue() {
		isPeriodic = true;
	}

	private ArrayList<Schedule> scheduleArray;
	private final String PERIODIC_NONE = "none";

	private final String PERIODIC_DAILY = "daily";

	private final String PERIODIC_WEEKLY = "weekly";
	private final String PERIODIC_MONTHLY = "monthly";

	private final int REPEAT_FREQUENCY_NONE = 1;

	private final int REPEAT_FREQUENCY_DAILY = 365;

	private final int REPEAT_FREQUENCY_WEEKLY = 52;

	private final int REPEAT_FREQUENCY_MONTHLY = 12;

	public final String value;

	/**
	 * Validates given period.
	 *
	 * @throws IllegalValueException
	 *             if given period string is invalid.
	 */
	public Periodic(String period) throws IllegalValueException {
		assert period != null;
		String trimmedPeriod = period.trim();
		if (!isValidPeriod(trimmedPeriod)) {
			throw new IllegalValueException(MESSAGE_PERIODIC_CONSTRAINTS);
		}
		this.value = trimmedPeriod;
		int repeatFrequency = parsePeriod(trimmedPeriod);
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Periodic // instanceof handles nulls
						&& this.value.equals(((Periodic) other).value)); // state
		// check
	}

	public ArrayList<Schedule> getScheduleArray() {
		return scheduleArray;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Parses the {@code String} period to {@code int}
	 *
	 * @param value
	 * @return period as integer
	 */
	public int parsePeriod(String value) {
		int repeatFrequency = REPEAT_FREQUENCY_NONE;
		switch (value) {
		case (PERIODIC_NONE):
			setPeriodicityFalse();
		case (PERIODIC_DAILY): {
			repeatFrequency = REPEAT_FREQUENCY_DAILY;
			setPeriodicityTrue();
		}
		case (PERIODIC_WEEKLY): {
			repeatFrequency = REPEAT_FREQUENCY_WEEKLY;
			setPeriodicityTrue();
		}
		case (PERIODIC_MONTHLY): {
			repeatFrequency = REPEAT_FREQUENCY_MONTHLY;
			setPeriodicityTrue();
		}
		}
		return repeatFrequency;
	}

	public void setupScheduleArray() {
	}

	@Override
	public String toString() {
		return value;
	}

}
