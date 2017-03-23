package seedu.bulletjournal.model.task;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the todo list.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "Task due date can take any values, and it should not be blank";

    /*
     * The first character of the due date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DUEDATE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public DueDate(String phone) throws IllegalValueException {
        assert phone != null;
        String trimmedPhone = phone.trim();
        if (!isValidDueDate(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid task phone number.
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(DUEDATE_VALIDATION_REGEX);
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
