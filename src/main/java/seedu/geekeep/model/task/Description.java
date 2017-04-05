package seedu.geekeep.model.task;

import seedu.geekeep.commons.exceptions.IllegalValueException;

//@@author A0121658E
/**
 * Represents a Task's description in GeeKeep. Guarantees: immutable; is valid as declared in
 * {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task description can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace, otherwise " " (a blank string) becomes a valid
     * input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    /**
     * Validates given description.
     *
     * @throws IllegalValueException
     *             if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        if (!isValidDescription(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
