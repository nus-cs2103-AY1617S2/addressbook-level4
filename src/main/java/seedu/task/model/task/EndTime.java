package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Task End Time should be given in ddmmyy tttt(24hour format)";
    public static final String TIME_VALIDATION_REGEX = "\\d{6} \\d{4}";
    public static final String EMPTY_TIME_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        String trimmedEndTime = endTime.trim();
        if (!isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = trimmedEndTime;
    }

    /**
     * Returns if a given string is a valid task end time.
     */
    public static boolean isValidTime(String test) {
        return (test.matches(TIME_VALIDATION_REGEX)  || test.matches(EMPTY_TIME_VALIDATION_REGEX));
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
