package seedu.tasklist.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;


/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = false)
    private String comment;
    @XmlElement(required = true)
    private String type;
    @XmlElement(required = false)
    private String priority;
    @XmlElement(required = false)
    private boolean status;
    @XmlElement(required = false)
    private Date deadline;
    @XmlElement(required = false)
    private Date startDate;
    @XmlElement(required = false)
    private Date endDate;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        comment = source.getComment().value;
        priority = source.getPriority().value;
        tagged = new ArrayList<>();
        type = source.getType();
        switch (type) {
        case DeadlineTask.TYPE:
            deadline = ((ReadOnlyDeadlineTask) source).getDeadline();
            break;
        case EventTask.TYPE:
            startDate = ((ReadOnlyEventTask) source).getStartDate();
            endDate = ((ReadOnlyEventTask) source).getEndDate();
            break;
        }
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Comment comment = new Comment(this.comment);
        final Priority priority = new Priority(this.priority);
        final Status status = new Status(this.status);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        switch (this.type) {
        case FloatingTask.TYPE:
            return new FloatingTask(name, comment, priority, status, tags);
        case DeadlineTask.TYPE:
            final Date deadline = this.deadline;
            return new DeadlineTask(name, comment, priority, status, deadline, tags);
        case EventTask.TYPE:
            final Date startDate = this.startDate;
            final Date endDate = this.endDate;
            return new EventTask(name, comment, priority, status, startDate, endDate, tags);
        default:
            return null;
        }
    }
}
