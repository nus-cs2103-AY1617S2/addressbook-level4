package seedu.task.model.task;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

	TaskName getTaskName();

	TaskDate getTaskDate();

	TaskTime getTaskStartTime();

	TaskTime getTaskEndTime();

	String getTaskDescription();

	/**
	 * Returns true if both have the same state. (interfaces cannot override
	 * .equals)
	 */
	default boolean isSameStateAs(ReadOnlyTask other) {
		return other == this // short circuit if same object
				|| (other != null // this is first to avoid NPE below
						&& other.getTaskName() != null && other.getTaskName().equals(this.getTaskName()) // state
																											// checks
																											// here
																											// onwards
						&& other.getTaskDate() != null && other.getTaskDate().equals(this.getTaskDate())
						&& other.getTaskStartTime() != null && other.getTaskStartTime().equals(this.getTaskStartTime())
						&& other.getTaskEndTime() != null && other.getTaskEndTime().equals(this.getTaskEndTime()));
	}

	/**
	 * Formats the task as text, showing all task details.
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getTaskName()).append(" Task Date: ").append(getTaskDate()).append(" Start Time: ")
				.append(getTaskStartTime()).append(" End Time: ").append(getTaskEndTime())
				.append(" Task Description: " + getTaskDescription());
		return builder.toString();
	}

}
