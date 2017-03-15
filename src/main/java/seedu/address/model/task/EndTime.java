package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's end time in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Task end time can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ENDTIME_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time string is invalid.
     */
    public EndTime(String endtime) throws IllegalValueException {
        assert endtime != null;
        if(endtime.isEmpty()) {
            this.value = endtime;
        } else {
            String trimmedEndTime = endtime.trim();
            if (!isValidEndTime(trimmedEndTime)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value = trimmedEndTime;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                        && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
