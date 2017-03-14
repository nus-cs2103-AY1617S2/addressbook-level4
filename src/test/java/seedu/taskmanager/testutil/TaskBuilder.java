package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.Deadline;
// import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.EndTime;

/**
 *
 */
public class TaskBuilder {

	private TestTask task;

	public TaskBuilder() {
		this.task = new TestTask();
	}

	/**
	 * Initializes the PersonBuilder with the data of {@code taskToCopy}.
	 */
	public TaskBuilder(TestTask taskToCopy) {
		this.task = new TestTask(taskToCopy);
	}

	public TaskBuilder withName(String taskname) throws IllegalValueException {
		this.task.setTaskName(new TaskName(taskname));
		return this;
	}

	/*
	 * public TaskBuilder withCategories(String ... categories) throws
	 * IllegalValueException { task.setCategories(new UniqueCategoryList()); for
	 * (String category: categories) { task.getCategories().add(new
	 * Category(category)); } return this; }
	 */

	public TaskBuilder withDate(String date) throws IllegalValueException {
		this.task.setDate(new Date(date));
		return this;
	}

	public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
		this.task.setDeadline(new Deadline(deadline));
		return this;
	}

	/*
	 * public TaskBuilder withStartTime(String starttime) throws
	 * IllegalValueException { this.task.setStartTime(new StartTime(starttime));
	 * return this; }
	 */

	public TestTask build() {
		return this.task;
	}

}
