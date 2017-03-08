package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

/**
 *
 */
public class TaskBuilder {

	private TestTask task;

	public TaskBuilder() {
		this.task = new TestTask();
	}

	/**
	 * Initializes the TaskBuilder with the data of {@code taskToCopy}.
	 */
	public TaskBuilder(TestTask taskToCopy) {
		this.task = new TestTask(taskToCopy);
	}

	public TaskBuilder withName(String name) throws IllegalValueException {
		this.task.setName(new Name(name));
		return this;
	}

	public TaskBuilder withTags(String... tags) throws IllegalValueException {
		this.task.setTags(new UniqueTagList());
		for (String tag : tags) {
			this.task.getTags().add(new Tag(tag));
		}
		return this;
	}

	public TaskBuilder withDescription(String address) throws IllegalValueException {
		this.task.setDescription(new Description(address));
		return this;
	}

	public TaskBuilder withPriority(String phone) throws IllegalValueException {
		this.task.setPriority(new Priority(phone));
		return this;
	}

	public TaskBuilder withDeadline(String email) throws IllegalValueException {
		this.task.setDeadline(new Deadline(email));
		return this;
	}

	public TestTask build() {
		return this.task;
	}

}
