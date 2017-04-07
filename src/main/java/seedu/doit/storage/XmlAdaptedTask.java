package seedu.doit.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.item.Task;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private boolean isDone;
    @XmlElement(nillable = true)
    private String startTime;
    @XmlElement(nillable = true)
    private String deadline;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
     * required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    // @@author A0138909R

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     *               XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        this.name = source.getName().fullName;
        this.priority = source.getPriority().value;
        if (source.hasStartTime()) {
            this.startTime = source.getStartTime().value;
        } else {
            this.startTime = null;
        }
        if (source.hasEndTime()) {
            this.deadline = source.getDeadline().value;
        } else {
            this.deadline = null;
        }
        this.description = source.getDescription().value;
        this.tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            this.tagged.add(new XmlAdaptedTag(tag));
        }
        this.isDone = source.getIsDone();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : this.tagged) {
            taskTags.add(tag.toModelType());
        }
        if (this.startTime == null) {
            this.startTime = "";
        }

        if (this.deadline == null) {
            this.deadline = "";
        }

        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime deadline = new EndTime(this.deadline);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, priority, startTime, deadline, description, tags, this.isDone);
    }
    // @@author
}
