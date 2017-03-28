package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */

public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "The format of the deadline should be DD-MMM-YY(YY),"
            + " where MMM are the first 3 letters which represent the months."
            + " Leading zeros of dates are allowed to be omitted.";

    public static final String DEADLINE_VALIDATION_REGEX = "^(?:(?:31(-)(?:0?[13578]|1[02]|"
            + "(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2]|"
            + "(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))"
            + "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)"
            + "?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))"
            + "$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))"
            + "|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        //assert date != null;
        //@@author A0139161J
        if (date.equals("")) {
            this.value = date;
        //@@author
        } else {
            String trimmedDate = date.trim();
            if (!isValidDeadline(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            this.value = trimmedDate;
        }
    }

    /**
     * Returns true if a given string is a valid deadline.
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
