package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.Priority;

/**
 *
 */
public class TaskBuilder {

    private TestTask Task;

    public TaskBuilder() {
        this.Task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code TaskToCopy}.
     */
    public TaskBuilder(TestTask TaskToCopy) {
        this.Task = new TestTask(TaskToCopy);
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.Task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.Task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.Task.setStartDate(new TaskDate(startDate));
        return this;
    }

    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.Task.setEndDate(new TaskDate(endDate));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        Task.setTags(new UniqueTagList());
        for (String tag: tags) {
            Task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.Task;
    }

}
