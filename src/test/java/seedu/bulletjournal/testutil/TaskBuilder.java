package seedu.bulletjournal.testutil;

import seedu.bullletjournal.commons.exceptions.IllegalValueException;
import seedu.bullletjournal.model.tag.Tag;
import seedu.bullletjournal.model.tag.UniqueTagList;
import seedu.bullletjournal.model.task.Deadline;
import seedu.bullletjournal.model.task.Description;
import seedu.bullletjournal.model.task.Detail;
import seedu.bullletjournal.model.task.Status;

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
        this.person.setName(new Description(name));
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
        this.person.setAddress(new Detail(address));
        return this;
    }

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Deadline(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Status(email));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
