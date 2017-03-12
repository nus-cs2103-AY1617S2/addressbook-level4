package seedu.tasklist.testutil;

import java.util.Date;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;

public class EventTaskBuilder extends TaskBuilder {
    private TestEventTask task;

    public EventTaskBuilder() {
        this.task = new TestEventTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public EventTaskBuilder(TestEventTask taskToCopy) {
        this.task = new TestEventTask(taskToCopy);
    }

    public EventTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public EventTaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public EventTaskBuilder withComment(String comment) throws IllegalValueException {
        this.task.setComment(new Comment(comment));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

    @Override
    public EventTaskBuilder withStatus(Boolean completed) throws IllegalValueException {
        this.task.setStatus(new Status(completed));
        return this;
    }

    @Override
    public EventTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public EventTaskBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setStartDate(new Date(date));
        return this;
    }

    public EventTaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setEndDate(new Date(date));
        return this;
    }
}
