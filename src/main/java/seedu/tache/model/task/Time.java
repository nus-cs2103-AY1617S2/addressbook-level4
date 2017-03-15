package seedu.tache.model.task;

import seedu.tache.commons.exceptions.IllegalValueException;

public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task time should only contain <CONSTRAINT>";

    public final String time;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        String trimmedStartTime = time.trim();
        /*if (!isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.time = trimmedStartTime;
    }

    /**
     * Returns true if a given string is a valid task time.
     */
    /*public static boolean isValidTime(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }*/

    @Override
    public String toString() {
        return time;
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
