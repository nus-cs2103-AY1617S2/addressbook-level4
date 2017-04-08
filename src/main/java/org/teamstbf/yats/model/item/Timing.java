package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class Timing {

	public static final String MESSAGE_TIMING_CONSTRAINTS = "Task timing should be in the 12 hour format, followed by am|pm "
			+ "and the hour and minutes should be separated by ':'";
	public static final String TIMING_VALIDATION_REGEX = "((1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)|^(?![\\s\\S])) (^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$|^(?![\\s\\S]))";

	public final String value;

	/**
	 * Validates given email.
	 *
	 * @throws IllegalValueException
	 *             if given email address string is invalid.
	 */
	public Timing(String timing) throws IllegalValueException {
		assert timing != null;
		String trimmedTiming = timing.trim();
		if (!isValidTiming(trimmedTiming)) {
			throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
		}
		this.value = trimmedTiming;
	}

	/**
	 * Returns if a given string is a valid person email.
	 */
	public static boolean isValidTiming(String test) {
		return test.matches(TIMING_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Timing // instanceof handles nulls
						&& this.value.equals(((Timing) other).value)); // state
		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
