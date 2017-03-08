package seedu.tache.model.task;

import seedu.tache.commons.exceptions.IllegalValueException;

public class Time {
    
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task time should only contain <CONSTRAINT>";
    
    public final String startTime;
    public final String endTime;
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String startTime, String endTime) throws IllegalValueException {
        assert startTime != null;
        String trimmedStartTime = startTime.trim();
        /*if (!isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.startTime = trimmedStartTime;
        
        assert endTime != null;
        String trimmedEndTime = endTime.trim();
        /*if (!isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.endTime = trimmedEndTime;
    }

    /**
     * Returns true if a given string is a valid task time.
     */
    /*public static boolean isValidTime(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }*/

    @Override
    public String toString() {
        return "Start: "+startTime + " End: " + endTime;
    }

   /* @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.time.equals(((Time) other).time)); // state check
    }*/

    /*@Override
    public int hashCode() {
        return (startTime.hashCode() && endTime.hashCode());
    }*/
}
