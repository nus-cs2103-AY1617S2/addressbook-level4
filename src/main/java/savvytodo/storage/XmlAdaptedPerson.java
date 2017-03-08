package savvytodo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Email;
import savvytodo.model.task.Name;
import savvytodo.model.task.Task;
import savvytodo.model.task.Phone;
import savvytodo.model.task.ReadOnlyTask;

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
        for (Category tag : source.getCategories()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Category> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final UniqueCategoryList tags = new UniqueCategoryList(personTags);
        return new Task(name, phone, email, address, tags);
    }
}
