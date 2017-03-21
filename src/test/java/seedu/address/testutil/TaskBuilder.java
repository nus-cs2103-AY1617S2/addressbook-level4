package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Information;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.TaskName;

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

    public TaskBuilder withName(String taskName) throws IllegalValueException {
        this.task.setName(new TaskName(taskName));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withInformation(String information) throws IllegalValueException {
        this.task.setInformation(new Information(information));
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new Deadline(deadline));
        return this;
    }

    public TaskBuilder withPriorityLevel(String priorityLevel) throws IllegalValueException {
        this.task.setPriority(new PriorityLevel(priorityLevel));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
