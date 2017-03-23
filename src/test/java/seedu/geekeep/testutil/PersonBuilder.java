package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.Title;

/**
 *
 */
public class PersonBuilder {

    private TestTask task;

    public PersonBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code taskToCopy}.
     */
    public PersonBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TestTask build() {
        return this.task;
    }

    public PersonBuilder withEndDateTime(String phone) throws IllegalValueException {
        this.task.setEndDateTime(new DateTime(phone));
        return this;
    }

    public PersonBuilder withLocation(String address) throws IllegalValueException {
        this.task.setLocation(new Location(address));
        return this;
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.task.setTitle(new Title(name));
        return this;
    }

    public PersonBuilder withStartDateTime(String email) throws IllegalValueException {
        this.task.setStartDateTime(new DateTime(email));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

}
