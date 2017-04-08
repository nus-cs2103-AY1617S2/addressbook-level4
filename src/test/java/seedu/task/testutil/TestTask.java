package seedu.task.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskPath;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskTime;

//@@author A0146757R
/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	private UniqueTagList tags;
	private TaskName taskName;
	private TaskDate taskDate;
	private TaskTime taskStartTime;
	private TaskTime taskEndTime;
	private String taskDescription;
	private TaskStatus taskStatus;
	private LocalDate localDate;
	private LocalTime localTime;

	public TestTask() {
		tags = new UniqueTagList();

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
		this.taskStatus = taskToCopy.getTaskStatus();
		this.tags = taskToCopy.getTags();
	}

	@Override
	public TaskName getTaskName() {
		return taskName;
	}

	public void setTaskName(TaskName name) {
		this.taskName = name;
	}

	@Override
	public TaskDate getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(TaskDate taskDate) {
		this.taskDate = taskDate;
	}

	@Override
	public TaskTime getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(TaskTime taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	@Override
	public TaskTime getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(TaskTime taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	@Override
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	@Override
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public UniqueTagList getTags() {
		return tags;
	}

	public void setTags(UniqueTagList tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return getAsText();
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add" + this.getTaskName().fullTaskName + " ");
		sb.append("d/" + this.getTaskDate().value + " ");
		sb.append("s/" + this.getTaskStartTime().value + " ");
		sb.append("e/" + this.getTaskEndTime().value + " ");
		sb.append("m/" + this.getTaskDescription() + " ");
		sb.append(this.getTaskStatus() + " ");
		this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
		return sb.toString();
	}

	public String getTaskPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCreationTime() {
		localDate = LocalDate.now();
		localTime = LocalTime.now();
	}

	public LocalDate getLocalDate() {
		if (localDate == null) {
			setCreationTime();
		}
		return localDate;
	}

	public LocalTime getLocalTime() {
		if (localTime == null) {
			setCreationTime();
		}
		return localTime;
	}

	private void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	private void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}
}
// @@author
