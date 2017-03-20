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
import seedu.address.model.task.StartTime;
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
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String urgencyLevel;
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
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().title;
        venue = source.getVenue().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        urgencyLevel = source.getUrgencyLevel().value;
        description = source.getDescription().value;
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
        final StartTime startTime = new StartTime(this.startTime);
        final EndTime endTime = new EndTime(this.endTime);
        final UrgencyLevel urgencyLevel = new UrgencyLevel(this.urgencyLevel);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, venue, startTime, endTime, urgencyLevel, description, tags);
    }
}
