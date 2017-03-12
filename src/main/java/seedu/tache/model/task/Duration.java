package seedu.tache.model.task;

import seedu.tache.commons.exceptions.IllegalValueException;

public class Duration {

    public static final String MESSAGE_DURATION_CONSTRAINTS =
            "Task duration should only contain alphanumeric characters and spaces";

    //public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String duration;

    /**
     * Validates given duration.
     *
     * @throws IllegalValueException if given duration string is invalid.
     */
    public Duration(String duration) throws IllegalValueException {
        assert duration != null;
        String trimmedDuration = duration.trim();
        /*if (!isValidName(trimmedDuration)) {
            throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
        }*/
        this.duration = trimmedDuration;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
/*    public static boolean isValidDuration(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }*/


    @Override
    public String toString() {
        return duration;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && this.duration.equals(((Duration) other).duration)); // state check
    }

    @Override
    public int hashCode() {
        return duration.hashCode();
    }
}
