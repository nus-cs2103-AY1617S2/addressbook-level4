package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the deadline of a task in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */

public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "The format of the DEADLINE should be DDMMYY"
            + ", represented in 6 digits";
    public static final String DEADLINE_VALIDATION_REGEX = "^[0-9]{6}$"; //TODO: update regex for better matching

    public final String value;

    /**
     * Validates a given input date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        //assert date != null;
        if (date.equals("")) {
            this.value = date;
        } else {
            String trimmedDate = date.trim();
            if (!isValidDeadline(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            this.value = trimmedDate;
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
