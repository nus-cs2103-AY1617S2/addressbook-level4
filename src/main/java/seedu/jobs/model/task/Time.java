package seedu.jobs.model.task;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time signature in JOBS. Guarantees: immutable; is valid
 * as declared in {@link #isValidTime(String)}
 */

public class Time {

    public static final String MESSAGE_TIME_CONSTRAINT = "Time should always follow the dd/mm/yy hh:mm format";
    public static final String TIME_VALIDATION_REGEX = "^[0-3]*[0-9]/[0-3]*[0-9]/(?:[0-9][0-9])?[0-9][0-9]\\s+(0*[1-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])";

    public static final String DEFAULT_TIME = "";
    public String value;

    /**
     * Validates given time-values input.
     *
     * @throws IllegalValueException
     *             if the input is invalid
     */
    public Time(Optional<String> startTime) throws IllegalValueException {
        if (!startTime.isPresent()) {
            this.value = DEFAULT_TIME;
        }
        else {
        	if(startTime.get().equals("")){
        		this.value = DEFAULT_TIME;
        	}
        	else if (!isValidTime(startTime.get())) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINT);
            }
        	else{
                this.value = startTime.get();
        	}
        }
    }

    /**
     * Returns true if a given string is in valid time format
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                        && this.value.equals(((Time) other).value)); // state
                                                                     // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
