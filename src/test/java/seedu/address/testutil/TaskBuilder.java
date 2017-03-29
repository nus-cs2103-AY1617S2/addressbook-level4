package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        try {
            this.task = new TestTask();
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        HashSet<Tag> setTags = new HashSet<Tag>();
        for (String tag : tags) {
            setTags.add(new Tag(tag));
        }
        task.setTags(new UniqueTagList(setTags));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
