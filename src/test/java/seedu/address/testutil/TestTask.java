package seedu.address.testutil;

import seedu.address.model.task.Description;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	private Name name;
	private Description description;
	private Deadline deadline;
	private Priority priority;
	private UniqueTagList tags;

	public TestTask() {
		this.tags = new UniqueTagList();
	}

	/**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.priority = taskToCopy.getPriority();
        this.deadline = taskToCopy.getDeadline();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();

	public void setName(Name name) {
		this.name = name;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setTags(UniqueTagList tags) {
		this.tags = tags;
	}

	@Override
	public Name getName() {
		return this.name;
	}

	@Override
	public Priority getPriority() {
		return this.priority;
	}

	@Override
	public Deadline getDeadline() {
		return this.deadline;
	}

	@Override
	public Description getDescription() {
		return this.description;
	}

	@Override
	public UniqueTagList getTags() {
		return this.tags;
	}

	@Override
	public String toString() {
		return getAsText();
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getName().fullName + " ");
		sb.append("d/" + this.getDescription().value + " ");
		sb.append("p/" + this.getPriority().value + " ");
		sb.append("e/" + this.getDeadline().value + " ");
		this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
		return sb.toString();
	}
}
