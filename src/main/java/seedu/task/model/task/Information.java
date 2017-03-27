package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's information in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidInformation(String)}
 */

public class Information {

    public static final String MESSAGE_INFORMATION_CONSTRAINTS =
            "Task's information can take any values, and it should not be blank";

    /*
     * The first character of the information must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INFORMATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates a given Task's information.
     *
     * @throws IllegalValueException if given Task's information string is invalid.
     */
    public Information(String info) {
        /*assert info != null;
        if (!isValidInformation(info)) {
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }*/
        this.value = info;
    }

    /**
     * Returns true if a given string is a valid Task's information.
     */
    public static boolean isValidInformation(String test) {
        return test.matches(INFORMATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Information // instanceof handles nulls
                && this.value.equals(((Information) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
