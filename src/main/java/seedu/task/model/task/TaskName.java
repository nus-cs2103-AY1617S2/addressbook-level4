package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager. Guarantees: immutable; is valid
 * as declared in {@link #isValidTaskName(String)}
 */
public class TaskName implements Comparable<TaskName> {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * The first character of the task must not be a whitespace, otherwise " "
     * (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTaskName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskName(String name) throws IllegalValueException {
	assert name != null;
	String trimmedName = name.trim();
	if (!isValidTaskName(trimmedName)) {
	    throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
	}
	this.fullTaskName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTaskName(String test) {
	return test.matches(NAME_VALIDATION_REGEX);
    }

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

    public int compareTo(TaskName other) {
	String x = new String(fullTaskName.toLowerCase());
	String y = new String(other.fullTaskName.toLowerCase());
	for (int i = 0; i < x.length() && i < y.length(); i++) {
	    if (x.charAt(i) < y.charAt(i)) {
		return -1;
	    } else if (x.charAt(i) > y.charAt(i)) {
		return 1;
	    }
	}
	if (x.length() > y.length()) {
	    return 1;
	} else if (x.length() < y.length()) {
	    return -1;
	} else {
	    return 0;
	}

    }

}
