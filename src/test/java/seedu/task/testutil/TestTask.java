package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	private TaskName taskName;
	private TaskDate taskDate;
	private TaskTime taskStartTime;
	private TaskTime taskEndTime;
	private String taskDescription;

	public TestTask() {
	}

	/**
	 * Creates a copy of {@code taskToCopy}.
	 */
	public TestTask(TestTask taskToCopy) {
		this.taskName = taskToCopy.getTaskName();
		this.taskDate = taskToCopy.getTaskDate();
		this.taskStartTime = taskToCopy.getTaskStartTime();
		this.taskEndTime = taskToCopy.getTaskEndTime();
		this.taskDescription = taskToCopy.getTaskDescription();
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

	public void setTaskStartTime(TaskTime startTime) {
		this.taskStartTime = startTime;
	}

	@Override
	public TaskTime getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(TaskTime endTime) {
		this.taskEndTime = endTime;
	}

	@Override
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String description) {
		this.taskDescription = description;
	}

	@Override
	public String toString() {
		return getAsText();
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getTaskName().fullTaskName + " ");
		sb.append("d/" + this.getTaskDate().value + " ");
		sb.append("s/" + this.getTaskStartTime().value + " ");
		sb.append("e/" + this.getTaskEndTime().value + " ");
		sb.append("de/" + this.getTaskDescription() + " ");

		return sb.toString();
	}

}
