package seedu.bulletjournal.testutil;

import seedu.bullletjournal.commons.exceptions.IllegalValueException;
import seedu.bullletjournal.model.tag.Tag;
import seedu.bullletjournal.model.tag.UniqueTagList;
import seedu.bullletjournal.model.task.Detail;
import seedu.bullletjournal.model.task.Status;
import seedu.bullletjournal.model.task.Description;
import seedu.bullletjournal.model.task.Deadline;

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
        this.person.setName(new Description(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Detail(address));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Deadline(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Status(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
