package t16b4.yats.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import t16b4.yats.commons.exceptions.IllegalValueException;
import t16b4.yats.model.item.Description;
import t16b4.yats.model.item.Email;
import t16b4.yats.model.item.Event;
import t16b4.yats.model.item.Location;
import t16b4.yats.model.item.Periodic;
import t16b4.yats.model.item.Title;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.Timing;
import t16b4.yats.model.item.Phone;
import t16b4.yats.model.item.ReadOnlyEvent;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.tag.Tag;
import t16b4.yats.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

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


    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyEvent source) {
        title = source.getTitle().fullName;
        location = source.getLocation().value;
        period = source.getPeriod().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        description = source.getDescription().value;
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
    public Event toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Location location = new Location(this.location);
        final Periodic period = new Periodic(this.period);
        final Timing startTime = new Timing(this.startTime);
        final Timing endTime = new Timing(this.endTime);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Event(title,location,period,startTime,endTime,description,tags);
    }
}
