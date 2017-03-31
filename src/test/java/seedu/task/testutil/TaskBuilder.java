package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.TaskId;

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

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    //@@author A0163744B
    public TaskBuilder withDuration(String startDate, String endDate) throws IllegalValueException {
        this.task.setDuration(new Duration(startDate, endDate));
        return this;
    }
    //@@author

    public TaskBuilder withDueDate(String dueDate) throws IllegalValueException {
        this.task.setDueDate(new DueDate(dueDate));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withTaskId(long id) throws IllegalValueException {
        this.task.setTaskId(new TaskId(id));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

    //@@author A0163744B
    public TaskBuilder withCompletion(boolean isComplete) {
        this.task.setCompletion(new Complete(isComplete));
        return this;
    }
}
