package seedu.bullletjournal.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.bullletjournal.commons.exceptions.IllegalValueException;
import seedu.bullletjournal.model.tag.Tag;
import seedu.bullletjournal.model.tag.UniqueTagList;
import seedu.bullletjournal.model.task.Deadline;
import seedu.bullletjournal.model.task.Description;
import seedu.bullletjournal.model.task.Detail;
import seedu.bullletjournal.model.task.ReadOnlyTask;
import seedu.bullletjournal.model.task.Status;
import seedu.bullletjournal.model.task.Task;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

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
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
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
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Description description = new Description(this.name);
        final Deadline deadline = new Deadline(this.phone);
        final Status status = new Status(this.email);
        final Detail detail = new Detail(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(description, deadline, status, detail, tags);
    }
}
