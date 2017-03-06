package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task End Date should be given in ddmmyy";
    public static final String DATE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given end date.
     *
     * @throws IllegalValueException if given end date is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
        assert endDate != null;
        String trimmedEndDate = endDate.trim();
        if (!isValidDate(trimmedEndDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedEndDate;
    }

    /**
     * Returns if a given string is a valid task end date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.value.equals(((EndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
