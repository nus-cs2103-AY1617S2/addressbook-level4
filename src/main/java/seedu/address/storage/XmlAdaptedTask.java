package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Location;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String location;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        remark = source.getRemark().value;
        location = source.getLocation().value;
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
        final Name name = new Name(this.name);
        final Date date = new Date(this.date);
        final Remark remark = new Remark(this.remark);
        final Location location = new Location(this.location);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, date, remark, location, tags);
    }
}
