package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 *
 */
public class TaskBuilder {

    private Task task;

    public TaskBuilder() {
        try {
            this.task = new Task();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
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

    public Task build() {
        return this.task;
    }

}
