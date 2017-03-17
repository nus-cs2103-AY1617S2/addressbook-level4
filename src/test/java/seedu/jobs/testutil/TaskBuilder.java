package seedu.jobs.testutil;

import java.util.Optional;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Address;
import seedu.jobs.model.task.Email;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Phone;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask personToCopy) {
        this.person = new TestTask(personToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(Optional.of(name)));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Address(address));
        return this;
    }

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Phone(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Email(email));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
