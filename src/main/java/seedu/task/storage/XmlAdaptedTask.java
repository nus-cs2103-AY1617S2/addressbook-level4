package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Address;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.Email;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.Task;
import seedu.task.model.task.Phone;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskTime;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        taskName = source.getTaskName().fullName;
        date = source.getTaskDate().value;
        startTime = source.getTaskStartTime().value;
        endTime = source.getTaskEndTime().value;
        description = source.getTaskDescription();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final TaskName taskName = new TaskName(this.taskName);
        final TaskDate taskDate = new TaskDate(this.date);
        final TaskTime taskStartTime = new TaskTime(this.startTime);
        final TaskTime taskEndTime = new TaskTime(this.endTime);
        final String taskDescription = this.description;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, tags);
    }
}
