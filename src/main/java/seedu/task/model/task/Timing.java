//@@author A0164212U
package seedu.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's timing in the address book. Guarantees: immutable; is valid
 * as declared in {@link #isValidTiming(String)}
 */
public class Timing implements Comparable<Timing> {

	public static final String MESSAGE_TIMING_CONSTRAINTS =
			"Task timing should be in the format HH:mm dd/MM/yyyy OR dd/MM/yyyy";
	public static final String[] TIMING_FORMAT = {
			"HH:mm dd/MM/yyyy", "dd/MM/yyyy" };
	public static final String NULL_TIMING = "n/a";
	public final String value;
	private Date timing;

	/**
	 * Validates given timing.
	 *
	 * @throws IllegalValueException
	 *             if given timing string is invalid.
	 */
	public Timing(String time) throws IllegalValueException {
		if (time != null) {
			String trimmedTiming = time.trim();
			if (!isValidTiming(trimmedTiming)) {
				throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
			}
			this.value = trimmedTiming;
			setTiming(time);
		} else {
			this.value = NULL_TIMING;
			this.timing = null;
		}
	}

	/**
	 * Returns if a given string is a valid timing.
	 */
	public static boolean isValidTiming(String test) {
		boolean isValid = false;
		if (test.equals(NULL_TIMING)) {
			isValid = true;
		} else {
			for (int i = 0; i < TIMING_FORMAT.length; i++) {
				SimpleDateFormat sdf = new SimpleDateFormat(TIMING_FORMAT[i]);
				sdf.setLenient(false);
				try {
					// throws ParseException if timing is not valid
					Date date = sdf.parse(test);
					// check if year is truly 4 digits (the 'yyyy' regex does not support this)
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					System.out.println(cal.get(Calendar.YEAR));
					if(cal.get(Calendar.YEAR) >= 1000 && cal.get(Calendar.YEAR) <= 9999){
						isValid = true;
					}
					break;
				} catch (ParseException e) {
				}
			}
		}
		return isValid;
	}

	public void setTiming(String time){
		for (int i = 0; i < TIMING_FORMAT.length; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat(TIMING_FORMAT[i]);
			sdf.setLenient(false);
			try {
				// throws ParseException if timing is not valid
				Date date = sdf.parse(time);
				this.timing = date;
				break;
			} catch (ParseException e) {
			}
		}
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		if (this.value == null) {
			return false;
		}
		return other == this // short circuit if same object
				|| (other instanceof Timing // instanceof handles nulls
						&& this.value.equals(((Timing) other).value)); // state
		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	//@@author A0163559U
	/**
	 * Results in Timing sorted in ascending order.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(Timing compareTiming) {
		boolean thisNull = this.timing == null;
		boolean otherNull = compareTiming.timing == null;

		if (thisNull && otherNull) {
			return 0;
		} else if (thisNull) {
			return 1;
		} else if (otherNull) {
			return -1;
		}

		boolean thisNullTiming = this.timing.equals(NULL_TIMING);
		boolean otherNullTiming = compareTiming.timing.equals(NULL_TIMING);

		if (thisNullTiming && otherNullTiming) {
			return 0;
		} else if (thisNullTiming) {
			return 1;
		} else if (otherNullTiming) {
			return -1;
		}

		int compareToResult = this.timing.getYear() - compareTiming.timing.getYear() + 3800;

		if (compareToResult == 0) {
			compareToResult = this.timing.getMonth() - compareTiming.timing.getMonth();
		}

		if (compareToResult == 0) {
			compareToResult = this.timing.getDay() - compareTiming.timing.getDay();
		}
		return compareToResult;
	}
}
