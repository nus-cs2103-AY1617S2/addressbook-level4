package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDateTime;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask personToCopy) {
        this.person = new TestTask(personToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        person.setTags(new UniqueTagList());
        for (String tag : tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String phone) throws IllegalValueException {
        this.person.setDescription(new Description(phone));
        return this;
    }

    public TaskBuilder withStartDateTime(String email) throws IllegalValueException {
        this.person.setStartDateTime(new StartDateTime(email));
        return this;
    }

    public TaskBuilder withEndDateTime(String address) throws IllegalValueException {
        this.person.setEndDateTime(new EndDateTime(address));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
