package seedu.task.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskTime;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

	@XmlElement(required = true)
	private String taskName;
	@XmlElement(required = true)
	private String taskDate;
	@XmlElement(required = true)
	private String taskStartTime;
	@XmlElement(required = true)
	private String taskEndTime;
	@XmlElement(required = true)
	private String taskDescription;

	/**
	 * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
	 * required by JAXB.
	 */
	public XmlAdaptedTask() {
	}

	/**
	 * Converts a given Task into this class for JAXB use.
	 *
	 * @param source
	 *            future changes to this will not affect the created
	 *            XmlAdaptedPerson
	 */
	public XmlAdaptedTask(ReadOnlyTask source) {
		taskName = source.getTaskName().fullTaskName;
		taskDate = source.getTaskDate().value;
		taskStartTime = source.getTaskStartTime().value;
		taskEndTime = source.getTaskEndTime().value;
		taskDescription = source.getTaskDescription();

	}

	/**
	 * Converts this jaxb-friendly adapted person object into the model's Task
	 * object.
	 *
	 * @throws IllegalValueException
	 *             if there were any data constraints violated in the adapted
	 *             task
	 */
	public Task toModelType() throws IllegalValueException {

		final TaskName taskName = new TaskName(this.taskName);
		final TaskDate taskDate = new TaskDate(this.taskDate);
		final TaskTime taskStartTime = new TaskTime(this.taskStartTime);
		final TaskTime taskEndTime = new TaskTime(this.taskEndTime);
		final String taskDescription = this.taskDescription;
		return new Task(taskName, taskDate, taskStartTime, taskEndTime, taskDescription);
	}
}
