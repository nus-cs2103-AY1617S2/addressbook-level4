package seedu.address.model.task;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

/**
 * Represents a Task's end time in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime implements Comparable<EndTime>{

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Task starttime numbers should only contain numbers";
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
        } catch (IllegalValueException e){
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }
    
    public LocalDateTime getEndTime(){
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

    @Override
    public int compareTo(EndTime other) {
        return this.getEndTime().compareTo(other.getEndTime());
    }

}