package todolist.model.task;

import java.time.LocalDateTime;

import todolist.commons.exceptions.IllegalValueException;
import todolist.commons.util.StringUtil;

/**
 * Represents a Task's end time in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime implements Time {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "End time shouldn't be earlier thant start time!";

    public static final String ENDTIME_VALIDATION_REGEX = ".+";

    public final LocalDateTime endTime;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time string is invalid.
     */
    public EndTime(String endTimeArg) throws IllegalValueException {
        assert endTimeArg != null;
        endTimeArg = endTimeArg.trim();
        try {
            this.endTime = StringUtil.parseStringToTime(endTimeArg);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(StringUtil.TIME_CONSTRAINTS);
        }
    }

    @Override
    public LocalDateTime getTimeValue() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return this.endTime.format(StringUtil.DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                        && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.toString().hashCode();
    }

}
