package seedu.bulletjournal.model.task;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;

/**
 * Name morphed into description
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskName {

    public static final String MESSAGE_TASKNAME_CONSTRAINTS =
            "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASKNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskName(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TASKNAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(TASKNAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && this.fullName.equals(((TaskName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
