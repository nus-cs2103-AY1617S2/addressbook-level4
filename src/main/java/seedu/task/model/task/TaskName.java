package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager. Guarantees: immutable; is valid
 * as declared in {@link #isValidTaskName(String)}
 */
public class TaskName {

	public static final String MESSAGE_TASK_NAME_CONSTRAINTS = "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

	/*
	 * The first character of the address must not be a whitespace, otherwise
	 * " " (a blank string) becomes a valid input.
	 */
	public static final String TASK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

	public final String fullTaskName;

	/**
	 * Validates given name.
	 *
	 * @throws IllegalValueException
	 *             if given name string is invalid.
	 */
	public TaskName(String taskName) throws IllegalValueException {
		assert taskName != null;
		String trimmedTaskName = taskName.trim();
		if (!isValidTaskName(trimmedTaskName)) {
			throw new IllegalValueException(MESSAGE_TASK_NAME_CONSTRAINTS);
		}
		this.fullTaskName = trimmedTaskName;
	}

	/**
	 * Returns true if a given string is a valid task name.
	 */
	public static boolean isValidTaskName(String test) {
		return test.matches(TASK_NAME_VALIDATION_REGEX);
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

}
