//@@author A0105748B
package seedu.bulletjournal.model.task;


import seedu.bulletjournal.commons.exceptions.IllegalValueException;

/**
 * Email morphed into completion status of task
 * Represents a Task's status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Task status should be done/undone.";
    public static final String STATUS_VALIDATION_REGEX = "(done|undone)";

    public final String value;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given status string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        assert status != null;
        String trimmedEmail = status.trim().toLowerCase();
        if (!isValidStatus(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }
    //@@author

    /**
     * Returns if a given string is a valid task status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(STATUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
