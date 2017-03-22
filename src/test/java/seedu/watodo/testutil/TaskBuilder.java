package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;

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


    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new DateTime(phone));
        return this;
    }


    public TestTask build() {
        return this.person;
    }

}
