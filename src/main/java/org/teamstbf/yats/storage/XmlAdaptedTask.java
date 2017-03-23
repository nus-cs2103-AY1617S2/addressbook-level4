package org.teamstbf.yats.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String period;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String completed;


    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
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
    public XmlAdaptedTask(ReadOnlyEvent source) {
        title = source.getTitle().fullName;
        location = source.getLocation().value;
        period = source.getPeriod().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        description = source.getDescription().value;
        completed = source.getIsDone().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Location location = new Location(this.location);
        final Periodic period = new Periodic(this.period);
        final Schedule startTime = new Schedule(this.startTime);
        final Schedule endTime = new Schedule(this.endTime);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(personTags);
        final IsDone isDone = new IsDone(this.completed);
        return new Event(title, location, period, startTime, endTime, description, tags, isDone);
    }
}
