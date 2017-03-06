package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a task's start date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task Start Date should be given in dd/mm/yy";
    public static final String DATE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        String trimmedStartDate = startDate.trim();
        if (!isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedStartDate;
    }

    /**
     * Returns true if a given string is a valid task start date.
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
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
