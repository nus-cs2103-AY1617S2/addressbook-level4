package seedu.task.model.task;

import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.MiscDateUtil;

/**
 * Represents a Task's end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = ""; // Validation on this object is no longer necessary.
    public static final String TIME_VALIDATION_REGEX = ""; // Regex is no longer required.
    public static final String EMPTY_TIME_VALIDATION_REGEX = "";

    public final Date value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(Date endTime) throws IllegalValueException {
        if (!isValidTime(endTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = endTime;
    }

    /**
     * Returns if a given string is a valid task end time.
     */
    public static boolean isValidTime(Date test) {
        // TODO: Remove this later on.
        return true;
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                        && MiscDateUtil.compareDatesFuzzy(this.value, ((EndTime) other).value, 1000)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
