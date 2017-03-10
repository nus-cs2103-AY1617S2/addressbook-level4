package seedu.taskmanager.model.task;


import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String ENDDATE_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given email.
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
     * Returns if a given string is a valid person email.
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
