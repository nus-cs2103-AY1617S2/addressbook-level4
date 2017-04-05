//@@author A0147622H
package seedu.geekeep.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String description;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private String isDone;

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
        title = source.getTitle().title;
        endDateTime = source.getEndDateTime() == null ? "" : source.getEndDateTime().value;
        startDateTime = source.getStartDateTime() == null ? "" : source.getStartDateTime().value;
        description = source.getDescriptoin() == null ? "" : source.getDescriptoin().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        isDone = source.isDone() ? "True" : "False";
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
        final Title title = new Title(this.title);
        final DateTime endDateTime = convertStringToDateTime(this.endDateTime);
        final DateTime startDateTime = convertStringToDateTime(this.startDateTime);
        final Description description = convertStringToDescription(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final boolean isDone = this.isDone.equals("True");
        return new Task(title, startDateTime, endDateTime, description, tags, isDone);
    }

    private DateTime convertStringToDateTime(String date) throws IllegalValueException {
        return date.isEmpty() ? null : new DateTime(date);
    }

    private Description convertStringToDescription(String description) throws IllegalValueException {
        return description.isEmpty() ? null : new Description(description);
    }
}
