package seedu.doit.model.item;


import seedu.doit.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
        "Task End Time should be 2 alphanumeric/period strings separated by '@'";
    public static final String ENDTIME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        String trimmedEndTime = endTime.trim();
        if (!isValidEndTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = trimmedEndTime;
    }

    /**
     * Returns if a given string is a valid task deadline.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EndTime // instanceof handles nulls
            && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
