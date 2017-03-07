package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a task's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Task Start Time should be given in ddmmyy tttt(24hour format)";
    public static final String TIME_VALIDATION_REGEX = "\\d{6} \\d{4}";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        String trimmedStartTime = startTime.trim();
        if (!isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = trimmedStartTime;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
