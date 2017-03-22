package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.DateTime;

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
    
    public TaskBuilder withStartDate(String startDate) throws IllegalValueException{
        this.task.setStartDate(new DateTime(startDate));
        return this;
    }
    
    public TaskBuilder withEndDate(String endDate) throws IllegalValueException{
        this.task.setEndDate(new DateTime(endDate));
        return this;
    }
    
    public TaskBuilder withStatus(TaskStatus newStatus) throws IllegalValueException {
        this.task.setStatus(newStatus);
        return this;
    }
    
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            this.task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
