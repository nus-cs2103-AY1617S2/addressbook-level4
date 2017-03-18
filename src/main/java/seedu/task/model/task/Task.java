package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	private TaskName taskName;
	private TaskDate taskDate;
	private TaskTime taskStartTime;
	private TaskTime taskEndTime;
	private String taskDescription;

	public static final String MESSAGE_INVALID_TIME = "Start time cannot be after end time";

	/**
	 * Every field must be present and not null.
	 */
	public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime,
			String taskDescription) {
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;
		this.taskDescription = taskDescription;
		/*
		 * try { this.name = new TaskName("PLACEHOLDER NAME, SHOULD NOT SEE");
		 * this.phone = new Phone("123"); this.email = new
		 * Email("asdfads@gmail.com"); this.address = new
		 * Address("22 Acacia Avenue"); tags = new UniqueTagList(); } catch
		 * (IllegalValueException e) { System.out.println("error"); }
		 */
	}

	/**
	 * Creates a copy of the given ReadOnlyTask.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getTaskName(), source.getTaskDate(), source.getTaskStartTime(), source.getTaskEndTime(),
				source.getTaskDescription());
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public TaskName getTaskName() {
		return taskName;
	}

	public void setTaskName(TaskName taskName) {
		this.taskName = taskName;
	}

	@Override
	public TaskDate getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(TaskDate date) {
		this.taskDate = date;
	}

	@Override
	public TaskTime getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(TaskTime startTime) throws IllegalValueException {
		if (this.taskEndTime == null || this.taskEndTime.compareTo(startTime) >= 0) {
			this.taskStartTime = startTime;
		} else {
			throw new IllegalValueException(MESSAGE_INVALID_TIME);
		}
	}

	@Override
	public TaskTime getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(TaskTime endTime) throws IllegalValueException {
		if (this.taskStartTime == null || this.taskStartTime.compareTo(endTime) <= 0) {
			this.taskEndTime = endTime;
		} else {
			throw new IllegalValueException(MESSAGE_INVALID_TIME);
		}
	}

	@Override
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String description) {
		this.taskDescription = description;
	}

	/**
	 * Updates this task with the details of {@code replacement}.
	 */
	public void resetData(ReadOnlyTask replacement) {
		assert replacement != null;

		try {
			this.setTaskName(replacement.getTaskName());
			this.setTaskDate(replacement.getTaskDate());
			this.setTaskStartTime(replacement.getTaskStartTime());
			this.setTaskEndTime(replacement.getTaskEndTime());
			this.setTaskDescription(replacement.getTaskDescription());
		} catch (IllegalValueException ive) {
			System.out.println("error");
		}
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyTask // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyTask) other));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(taskName, taskDate, taskStartTime, taskEndTime, taskDescription);
	}

}
