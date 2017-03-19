package seedu.address.model.task;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

/**
 * Represents a Task's start time in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime implements Comparable<StartTime>{

    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Task starttime numbers should only contain numbers";
    public static final String STARTTIME_VALIDATION_REGEX = ".+";

    public final LocalDateTime startTime;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    public StartTime(String startTimeArg) throws IllegalValueException {
        assert startTimeArg != null;
        startTimeArg = startTimeArg.trim();
        try {
            this.startTime = StringUtil.parseStringToTime(startTimeArg);
        } catch (IllegalValueException e){
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX);
    }
    
    public LocalDateTime getStartTime(){
        return this.startTime;
    }
    
    @Override
    public String toString() {
        return this.startTime.format(StringUtil.DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                        && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.toString().hashCode();
    }

    @Override
    public int compareTo(StartTime other) {
        return this.startTime.compareTo(other.getStartTime());
    }

}