package typetask.model.task;

import typetask.commons.exceptions.IllegalValueException;

/**
 * Represents a task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
//@@author A0139926R
public class DueDate {
    public static final String MESSAGE_DATE_CONSTRAINTS = "Examples of task's dates: by tmr , today, next week 5pm";
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
        this.value = trimmedDate;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
