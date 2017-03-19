package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement
    private String venue;
    @XmlElement
    private String endTime;
    @XmlElement
    private String urgencyLevel;
    @XmlElement
    private String description;
    @XmlElement
    private Boolean status;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    //@@author A0122017Y
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().toString();
        venue = source.getVenue().toString();
        endTime = source.getEndTime().toString();
        urgencyLevel = source.getUrgencyLevel().toString();
        description = source.getDescription().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Venue venue = new Venue(this.venue);
        final EndTime endTime = new EndTime(this.endTime);
        final UrgencyLevel urgencyLevel = new UrgencyLevel(this.urgencyLevel);
        final Description description = new Description(this.description);
        final Boolean status = new Boolean(this.status);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, venue, endTime, urgencyLevel, description, status, tags);
    }
}
