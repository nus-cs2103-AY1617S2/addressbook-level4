package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.FromDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.ToDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Activity.
 */
public class XmlAdaptedActivity {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String starttime;
    @XmlElement(required = true)
    private String fromdate;
    @XmlElement(required = true)
    private String endtime;
    @XmlElement(required = true)
    private String todate;
    @XmlElement(required = true)
    private String bydate;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedActivity.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedActivity() {}


    /**
     * Converts a given Activity into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedActivity
     */
    public XmlAdaptedActivity(ReadOnlyActivity source) {
        description = source.getDescription().description;
        priority = source.getPriority().value;
        starttime = source.getStartTime().toString();
        fromdate = source.getFromDate().value;
        endtime = source.getEndTime().toString();
        todate = source.getToDate().value;
        bydate = source.getByDate().value;
        location = source.getLocation().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Activity object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final Priority priority = new Priority(this.priority);
        final StartTime starttime = new StartTime(this.starttime);
        final FromDate fromdate = new FromDate(this.fromdate);
        final EndTime endtime = new EndTime(this.endtime);
        final ToDate todate = new ToDate(this.todate);
        final ByDate bydate = new ByDate(this.bydate);
        final Location location = new Location(this.location);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Activity(description, priority, starttime, fromdate, endtime, todate, bydate, location, tags);
    }
}
