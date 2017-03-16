package seedu.taskmanager.model.task;


import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Start date should be of dd/mm/yyyy format or can be empty";
    public static final String ENDDATE_VALIDATION_REGEX = "(^$)|(^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$)";

    public final String value;

    /**
     * Validates given end date.
     *
     * @throws IllegalValueException if given end date string is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
        assert endDate != null;
        String trimmedEndDate = endDate.trim();
        if (!isValidEndDate(trimmedEndDate)) {
            throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
        }
        this.value = trimmedEndDate;
    }

    /**
     * Returns if a given string is a valid task end date.
     */
    public static boolean isValidEndDate(String test) {
        return test.matches(ENDDATE_VALIDATION_REGEX);
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
