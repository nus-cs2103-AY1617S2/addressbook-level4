package seedu.task.model.task;

import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;

//@@author A0146789H
/**
 * Represents a task's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = ""; // Validation no longer necessary
    public static final String TIME_VALIDATION_REGEX = ""; // Regex validation no longer necessary.
    public static final String EMPTY_TIME_VALIDATION_REGEX = "";

    public final Date value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public StartTime(Date startTime) throws IllegalValueException {
        assert startTime != null;
        if (!isValidTime(startTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = startTime;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidTime(Date test) {
        //return (test.matches(TIME_VALIDATION_REGEX) || test.matches(EMPTY_TIME_VALIDATION_REGEX));
        return true;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        return value.toString();
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
