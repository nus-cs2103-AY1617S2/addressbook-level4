package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ClockTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    //private final Logger logger = LogsCenter.getLogger(XmlAdaptedTask.class);

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String clockTime;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String priority;

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
        time = source.getTime().value;
        clockTime = source.getClockTime().value;
        priority = source.getPriority().priorityLevel;
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

        /*
        logger.setLevel(Level.INFO);
        logger.info("name: " + this.name);
        logger.info("time: " + this.time);
        logger.info("clockTime: " + this.clockTime);
        */

        final Name name = new Name(this.name);
        final Time time = new Time(this.time);
        final ClockTime clockTime = new ClockTime(this.clockTime);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Status status = new Status(0);
        return new Task(name, time, clockTime, priority, tags, status);
    }
}
