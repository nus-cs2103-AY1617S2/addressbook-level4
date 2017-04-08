package project.taskcrusher.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/**
 * JAXB-friendly version of Event.
 */
public class XmlAdaptedEvent {

    /* Attributes inherited from UserToDo*/
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private boolean isComplete;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /* Event-specific attributes */
    @XmlElement(required = true)
    private List<XmlAdaptedTimeslot> timeslots = new ArrayList<>();
    @XmlElement(required = true)
    private String location;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getName().name;
        priority = source.getPriority().priority;
        description = source.getDescription().description;
        isComplete = source.isComplete();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        timeslots = new ArrayList<>();
        for (Timeslot timeslot: source.getTimeslots()) {
            timeslots.add(new XmlAdaptedTimeslot(timeslot));
        }

        location = source.getLocation().location;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }

        final List<Timeslot> eventTimeslots = new ArrayList<>();
        for (XmlAdaptedTimeslot timeslot: timeslots) {
            eventTimeslots.add(timeslot.toModelType());
        }

        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(eventTags);

        final Location location = new Location(this.location);

        return new Event(name, eventTimeslots, priority, location, description, tags, isComplete);
    }

}
