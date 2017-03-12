package seedu.tasklist.testutil;

import java.util.Date;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;

public class DeadlineTaskBuilder extends TaskBuilder {
    private TestDeadlineTask task;

    public DeadlineTaskBuilder() {
        this.task = new TestDeadlineTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public DeadlineTaskBuilder(TestDeadlineTask taskToCopy) {
        this.task = new TestDeadlineTask(taskToCopy);
    }

    public DeadlineTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public DeadlineTaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public DeadlineTaskBuilder withComment(String comment) throws IllegalValueException {
        this.task.setComment(new Comment(comment));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

    @Override
    public DeadlineTaskBuilder withStatus(Boolean completed) throws IllegalValueException {
        this.task.setStatus(new Status(completed));
        return this;
    }

    @Override
    public DeadlineTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public DeadlineTaskBuilder withDeadline(String date) throws IllegalValueException {
        this.task.setDeadline(new Date(date));
        return this;
    }
}
