package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 *
 */
public class PersonBuilder {

    private TestTask person;

    public PersonBuilder() {
        this.person = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(TestTask personToCopy) {
        this.person = new TestTask(personToCopy);
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withDescription(String address) throws IllegalValueException {
        this.person.setDescription(new Description(address));
        return this;
    }

    public PersonBuilder withPriority(String phone) throws IllegalValueException {
        this.person.setPriority(new Priority(phone));
        return this;
    }

    public PersonBuilder withDeadline(String email) throws IllegalValueException {
        this.person.setDeadline(new Deadline(email));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
