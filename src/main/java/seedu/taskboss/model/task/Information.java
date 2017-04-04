package seedu.taskboss.model.task;


import seedu.taskboss.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's information in TaskBoss.
 * Guarantees: immutable; is valid as declared in {@link #isValidInformation(String)}
 */
public class Information {

    private static final String EMPTY_STRING = "";

    public static final String MESSAGE_INFORMATION_CONSTRAINTS =
            "Task information can take any values, and it can be blank";

    /*
     * The first character of the information must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INFORMATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given information.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Information(String information) throws IllegalValueException {
        assert information != null;
        if (!isValidInformation(information)) {
            throw new IllegalValueException(MESSAGE_INFORMATION_CONSTRAINTS);
        }
        this.value = information;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidInformation(String test) {
        return test.matches(INFORMATION_VALIDATION_REGEX) ||
                EMPTY_STRING.equals(test);

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
