package seedu.address.model.task;



import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Deadline {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be entered as DD-MM-YYYY";

    /*
     * The deadline must have at least one visible character
     */
    public static final String DATE_VALIDATION_REGEX = "\\p{Graph}++";

    public String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        assert date != null;
		this.value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
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
