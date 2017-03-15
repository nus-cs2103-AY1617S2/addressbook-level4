package seedu.jobs.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Address;
import seedu.jobs.model.task.Email;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Person;
import seedu.jobs.model.task.Phone;
import seedu.jobs.model.task.ReadOnlyPerson;

/**
 * JAXB-friendly version of the Task.
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
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
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
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address description = new Address(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Person(name, phone, email, description, tags);
    }
}
