package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String priority;
	@XmlElement(required = true)
	private String deadline;
	@XmlElement(required = true)
	private String description;

	@XmlElement
	private List<XmlAdaptedTag> tagged = new ArrayList<>();

	/**
	 * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
	 * required by JAXB.
	 */
	public XmlAdaptedTask() {
	}

	/**
	 * Converts a given Person into this class for JAXB use.
	 *
	 * @param source
	 *            future changes to this will not affect the created
	 *            XmlAdaptedTask
	 */
	public XmlAdaptedTask(ReadOnlyTask source) {
		this.name = source.getName().fullName;
		this.priority = source.getPriority().value;
		this.deadline = source.getDeadline().value;
		this.description = source.getDescription().value;
		this.tagged = new ArrayList<>();
		for (Tag tag : source.getTags()) {
			this.tagged.add(new XmlAdaptedTag(tag));
		}
	}

	/**
	 * Converts this jaxb-friendly adapted task object into the model's Person
	 * object.
	 *
	 * @throws IllegalValueException
	 *             if there were any data constraints violated in the adapted
	 *             task
	 */
	public Task toModelType() throws IllegalValueException {
		final List<Tag> personTags = new ArrayList<>();
		for (XmlAdaptedTag tag : this.tagged) {
			personTags.add(tag.toModelType());
		}
		final Name name = new Name(this.name);
		final Priority priority = new Priority(this.priority);
		final Deadline deadline = new Deadline(this.deadline);
		final Description description = new Description(this.description);
		final UniqueTagList tags = new UniqueTagList(personTags);
		return new Task(name, priority, deadline, description, tags);
	}
}
