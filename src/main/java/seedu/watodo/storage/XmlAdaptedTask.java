package seedu.watodo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private String startDate;

    @XmlElement(required = false)
    private String endDate;

    @XmlElement(required = true)
    private String status;

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
        name = source.getDescription().fullDescription;
        if (source.getStartDate() != null) {
            startDate = source.getStartDate().toString();
        }

        if (source.getEndDate() != null) {
            endDate = source.getEndDate().toString();
        }

        status = source.getStatus().toString();

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
        final Description name = new Description(this.name);

        TaskStatus status = TaskStatus.UNDONE;

        if (this.status != null) {
            status = getStatusFromString(this.status);
        }

        final UniqueTagList tags = new UniqueTagList(taskTags);
        if (startDate == null && endDate == null) {
            return new Task(name, tags, status);

        } else if (startDate == null && endDate != null) {
            final DateTime eDate = new DateTime(endDate);
            return new Task(name, eDate, tags, status);

        } else if (startDate != null && endDate != null) {
            final DateTime sDate = new DateTime(startDate);
            final DateTime eDate = new DateTime(endDate);
            return new Task(name, sDate, eDate, tags, status);
        }

        return new Task(name, tags, status);
    }

    private TaskStatus getStatusFromString(String string) {
        switch (string) {
        case "Undone":
            return TaskStatus.UNDONE;
        case "Ongoing":
            return TaskStatus.ONGOING;
        case "Done":
            return TaskStatus.DONE;
        case "Overdue":
            return TaskStatus.OVERDUE;
        default:
            return TaskStatus.UNDONE;
        }
    }
}
