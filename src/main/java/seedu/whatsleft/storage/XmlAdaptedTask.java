package seedu.whatsleft.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0121668A
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime byTime;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate byDate;
    @XmlElement(required = true)
    private String location;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement(required = true)
    private boolean status;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

  //@@author A0148038A
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        description = source.getDescription().toString();
        priority = source.getPriority().toString();
        byTime = source.getByTime().getValue();
        byDate = source.getByDate().getValue();
        location = source.getLocation().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        status = source.getStatus();
    }
    //@@author A0121668A
    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final Priority priority = new Priority(this.priority);
        final ByTime byTime = new ByTime(this.byTime);
        final ByDate byDate = new ByDate(this.byDate);
        final Location location = new Location(this.location);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final boolean status = this.status;

        return new Task(description, priority, byTime, byDate, location, tags, status);

    }
}
