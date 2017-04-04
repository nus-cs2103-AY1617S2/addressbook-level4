// @@author A0139399J
package seedu.doit.model.item;

import seedu.doit.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in the task manager. Guarantees: immutable;
 * is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
        "Task description can take in any string";
    /*
     *  Any String is a valid description
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = ".*";

    public final String value;

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

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Description // instanceof handles nulls
            && this.value.equals(((Description) other).value)); // state check
    }

}
