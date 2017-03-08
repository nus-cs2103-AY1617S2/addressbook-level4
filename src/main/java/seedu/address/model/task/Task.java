package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the Task Manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	private Name name;
	private Priority priority;
	private Deadline deadline;
	private Description description;

	private UniqueTagList tags;

	/**
	 * Every field must be present and not null.
	 */
	public Task(Name name, Priority priority, Deadline deadline, Description description, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, priority, deadline, description, tags);
		this.name = name;
		this.priority = priority;
		this.deadline = deadline;
		this.description = description;
		this.tags = new UniqueTagList(tags); // protect internal tags from
												// changes in the arg list
	}

	/**
	 * Creates a copy of the given ReadOnlyTask.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getName(), source.getPriority(), source.getDeadline(), source.getDescription(), source.getTags());
	}

	public void setName(Name name) {
		assert name != null;
		this.name = name;
	}

	@Override
	public Name getName() {
		return this.name;
	}

	public void setPriority(Priority priority) {
		assert priority != null;
		this.priority = priority;
	}

	@Override
	public Priority getPriority() {
		return this.priority;
	}

	public void setDeadline(Deadline deadline) {
		assert deadline != null;
		this.deadline = deadline;
	}

	@Override
	public Deadline getDeadline() {
		return this.deadline;
	}

	public void setDescription(Description description) {
		assert description != null;
		this.description = description;
	}

	@Override
	public Description getDescription() {
		return this.description;
	}

	@Override
	public UniqueTagList getTags() {
		return new UniqueTagList(this.tags);
	}

	/**
	 * Replaces this task's tags with the tags in the argument tag list.
	 */
	public void setTags(UniqueTagList replacement) {
		this.tags.setTags(replacement);
	}

	/**
	 * Updates this task with the details of {@code replacement}.
	 */
	public void resetData(ReadOnlyTask replacement) {
		assert replacement != null;

		this.setName(replacement.getName());
		this.setPriority(replacement.getPriority());
		this.setDeadline(replacement.getDeadline());
		this.setDescription(replacement.getDescription());
		this.setTags(replacement.getTags());
	}

	@Override
	public boolean equals(Object other) {
		return (other == this // short circuit if same object
		) || ((other instanceof ReadOnlyTask // instanceof handles nulls
		) && this.isSameStateAs((ReadOnlyTask) other));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(this.name, this.priority, this.deadline, this.description, this.tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}
}
