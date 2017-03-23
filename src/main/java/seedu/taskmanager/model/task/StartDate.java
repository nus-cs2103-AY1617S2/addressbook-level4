package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "Start date should be of dd/mm/yyyy format or can be empty";
    public static final String STARTDATE_VALIDATION_REGEX =
            "(^$)|(^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$)";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        String trimmedStartDate = startDate.trim();
        if (!isValidStartDate(trimmedStartDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        this.value = trimmedStartDate;
    }

    /**
     * Returns true if a given string is a valid task start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
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
