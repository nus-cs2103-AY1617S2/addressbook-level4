package org.teamstbf.yats.model.item;

/**
 * Represents a Task's description in the task manager. Guarantees: immutable;
 * is valid as declared in {@link #isValidAddress(String)}
 */
public class Description {

	private static final int DESCRIPTION_MAXIMUM_LENGTH = 1000;

	public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Task description can take any values, but can only be 1000 characters in length. It can be left blank.";

	public final String value;

	public Description(String description) {
		this.value = description;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Description // instanceof handles nulls
						&& this.value.equals(((Description) other).value)); // state
		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Returns true if a given string is a valid Description. String should be
	 * blank("") and never null.
	 */
	public static boolean isValidDescription(String string) {
		if (string == null) {
			return false;
		} else if (string.length() > DESCRIPTION_MAXIMUM_LENGTH) {
			return false;
		} else {
			return true;
		}
	}

}
