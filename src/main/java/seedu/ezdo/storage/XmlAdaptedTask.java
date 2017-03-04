package seedu.ezdo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
<<<<<<< HEAD:src/main/java/seedu/ezdo/storage/XmlAdaptedPerson.java
import seedu.ezdo.model.todo.Address;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Person;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyPerson;
=======
import seedu.ezdo.model.todo.*;
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e:src/main/java/seedu/ezdo/storage/XmlAdaptedTask.java

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private int priority;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String startDate;

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
        priority = source.getPriority().value;
        email = source.getEmail().value;
        startDate = source.getStartDate().value;
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
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Email email = new Email(this.email);
<<<<<<< HEAD:src/main/java/seedu/ezdo/storage/XmlAdaptedPerson.java
        final Address address = new Address(this.address);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Person(name, priority, email, address, tags);
=======
        final StartDate startDate = new StartDate(this.startDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, phone, email, startDate, tags);
>>>>>>> 6228ae6b03115b0e64fed2b189d86d8b94d2fa7e:src/main/java/seedu/ezdo/storage/XmlAdaptedTask.java
    }
}
