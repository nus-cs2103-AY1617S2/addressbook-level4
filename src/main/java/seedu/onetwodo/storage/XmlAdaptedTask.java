package seedu.onetwodo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Recurring;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String recur;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String isDone;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        startDate = source.getStartDate().value;
        endDate = source.getEndDate().value;
        recur = source.getRecur().value;
        priority = source.getPriority().value;
        description = source.getDescription().value;
        isDone = String.valueOf(source.getDoneStatus());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final StartDate startDate = new StartDate(this.startDate);
        final EndDate endDate = new EndDate(this.endDate);
        final Recurring recur = new Recurring(this.recur);
        final Priority priority = new Priority(this.priority);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        Task task = new Task(name, startDate, endDate, recur, priority, description,
                tags, Boolean.valueOf(this.isDone));
        return task;
    }
}
