package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

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

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        this.task.setTags(new UniqueTagList());
        for (String tag : tags) {
            this.task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new StartTime(startTime));
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new EndTime(deadline));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
