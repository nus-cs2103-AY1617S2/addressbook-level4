package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;
import seedu.doit.model.task.Deadline;
import seedu.doit.model.task.Description;
import seedu.doit.model.task.Name;
import seedu.doit.model.task.Priority;

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

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String address) throws IllegalValueException {
        this.task.setDescription(new Description(address));
        return this;
    }

    public TaskBuilder withPriority(String phone) throws IllegalValueException {
        this.task.setPriority(new Priority(phone));
        return this;
    }

    public TaskBuilder withDeadline(String email) throws IllegalValueException {
        this.task.setDeadline(new Deadline(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
