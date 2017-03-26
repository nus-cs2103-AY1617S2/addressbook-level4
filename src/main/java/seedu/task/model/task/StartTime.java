package seedu.task.model.task;

import java.text.DateFormat;
import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.MiscDateUtil;

//@@author A0146789H
/**
 * Represents a task's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = ""; // Validation on string no longer necessary in model.

    public final Date value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public StartTime(Date startTime) throws IllegalValueException {
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
        if (this.value == null) {
            return "";
        }
        DateFormat formatter = DateFormat.getDateTimeInstance();
        return formatter.format(this.value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                        && MiscDateUtil.compareDatesFuzzy(this.value, ((StartTime) other).value, 1000)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
