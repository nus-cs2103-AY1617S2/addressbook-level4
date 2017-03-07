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

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(TestPerson personToCopy) {
        this.person = new TestPerson(personToCopy);
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
        this.person.setAddress(new Description(address));
        return this;
    }

    public PersonBuilder withPriority(String phone) throws IllegalValueException {
        this.person.setPhone(new Priority(phone));
        return this;
    }

    public PersonBuilder withDeadline(String email) throws IllegalValueException {
        this.person.setEmail(new Deadline(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
