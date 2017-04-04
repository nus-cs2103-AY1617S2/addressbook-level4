package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in ProcrastiNoMore. Guarantees: immutable; is valid
 * as declared in {@link #isValidTaskName(String)}
 */
public class TaskName {

    public static final String MESSAGE_TASKNAME_CONSTRAINTS = "Tasks names should only "
            + "contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String TASKNAME_VALIDATION_REGEX = "[^\\s].*";

    public final String fullTaskName;

    // @@author A0139520L
    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskName(String taskName) throws IllegalValueException {
        assert taskName != null;
        String trimmedTaskName = taskName.trim();
        if (!isValidName(trimmedTaskName)) {
            throw new IllegalValueException(MESSAGE_TASKNAME_CONSTRAINTS);
        }
        this.fullTaskName = trimmedTaskName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(TASKNAME_VALIDATION_REGEX);
    }

    // @@author
    @Override
    public String toString() {
        return fullTaskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                        && this.fullTaskName.equals(((TaskName) other).fullTaskName)); // state
                                                                                       // check
    }

    @Override
    public int hashCode() {
        return fullTaskName.hashCode();
    }

}
