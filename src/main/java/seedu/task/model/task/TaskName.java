package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskName(String)}
 */

public class TaskName {

    public static final String MESSAGE_TASKNAME_CONSTRAINTS =
            "Task's names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Task's name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASKNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String taskName;

    /**
     * Validates given Task's name.
     *
     * @throws IllegalValueException if given task name string is invalid.
     */
    public TaskName(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidTaskName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TASKNAME_CONSTRAINTS);
        }
        this.taskName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Task's name.
     */
    public static boolean isValidTaskName(String test) {
        return test.matches(TASKNAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && this.taskName.equals(((TaskName) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
