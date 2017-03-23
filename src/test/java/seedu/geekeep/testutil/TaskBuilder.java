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
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TestTask build() {
        return this.task;
    }

    public TaskBuilder withEndDateTime(String phone) throws IllegalValueException {
        this.task.setEndDateTime(new DateTime(phone));
        return this;
    }

    public TaskBuilder withLocation(String address) throws IllegalValueException {
        this.task.setLocation(new Location(address));
        return this;
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setTitle(new Title(name));
        return this;
    }

    public TaskBuilder withStartDateTime(String email) throws IllegalValueException {
        this.task.setStartDateTime(new DateTime(email));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

}
