package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completion status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompletionStatus(String)}
 */
public class CompletionStatus {

    public static final String MESSAGE_COMPLETION_STATUS_CONSTRAINTS =
            "Task completion status should be Done\"Not Done\"";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MESSAGE_COMPLETION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given completion status.
     *
     * @throws IllegalValueException if given completion status is invalid.
     */
    public CompletionStatus(String completionStatus) throws IllegalValueException {
        assert completionStatus != null;
        if (!isValidCompletionStatus(completionStatus)) {
            throw new IllegalValueException(MESSAGE_COMPLETION_STATUS_CONSTRAINTS);
        }
        this.value = completionStatus;
    }

    /**
     * Returns true if a given string is a valid completion status.
     */
    public static boolean isValidCompletionStatus(String test) {
        return test.matches(MESSAGE_COMPLETION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatus // instanceof handles nulls
                && this.value.equals(((CompletionStatus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
