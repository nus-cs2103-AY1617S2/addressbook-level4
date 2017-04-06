package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = false)
    private String dueDate;
    @XmlElement(required = false)
    private String start;
    @XmlElement(required = false)
    private String end;
    @XmlElement(required = true)
    private String complete;
    @XmlElement(required = true)
    private long id;

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
        description = source.getDescription().description;

        Duration duration = source.getDuration();
        start = duration != null ? duration.getStartString() : null;
        end = duration != null ? duration.getEndString() : null;
        dueDate = source.getDueDate() != null ?
                source.getDueDate().toString() :
                null;
        complete = source.getComplete().getString();
        id = source.getTaskId().id;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final Duration duration = this.start != null && this.end != null ?
            new Duration(this.start, this.end) :
            null;
        final DueDate dueDate = this.dueDate != null ?
                new DueDate(this.dueDate) :
                null;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Complete complete = this.complete.equals(Complete.COMPLETE_STRING) ?
            new Complete(true) : new Complete(false);
        final TaskId id = new TaskId(this.id);
        return new Task(description, dueDate, duration, tags, complete, id);
    }
}
