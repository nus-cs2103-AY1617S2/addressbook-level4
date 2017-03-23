//@@author A0124153U


package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completion in the ToDoApp
 * Guarantees: immutable; is valid as declared in {@link #isValidCompletion(String)}
 */
public class Completion {

    public static final String MESSAGE_COMPLETION_CONSTRAINTS =
            "Task Completion";

    /*
     * The first character of the completion must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMPLETION_VALIDATION_REGEX = "([^\\s])+";

    public final boolean value;

    /**
     * Validates given completion.
     *
     * @throws IllegalValueException if given completion string is invalid.
     */
    public Completion(String completion) throws IllegalValueException {
        assert completion != null;
        if (!isValidCompletion(completion)) {
            throw new IllegalValueException(MESSAGE_COMPLETION_CONSTRAINTS);
        }
        this.value = Boolean.parseBoolean(completion);
    }

    /**
     * Returns true if a given string is a valid completion.
     */
    public static boolean isValidCompletion(String test) {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Completion // instanceof handles nulls
                && (this.value == ((Completion) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(value).hashCode();
    }

}
