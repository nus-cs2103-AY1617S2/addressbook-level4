package t16b4.yats.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import t16b4.yats.commons.exceptions.IllegalValueException;
import t16b4.yats.model.item.Description;
import t16b4.yats.model.item.Email;
import t16b4.yats.model.item.Title;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.Phone;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.tag.Tag;
import t16b4.yats.model.tag.UniqueTagList;

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
    public XmlAdaptedPerson(ReadOnlyItem source) {
        name = source.getTitle().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getDescription().value;
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
        final Title name = new Title(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Description address = new Description(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(name, phone, email, address, tags);
    }
}
