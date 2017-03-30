package seedu.onetwodo.testutil;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.Recurring;
import seedu.onetwodo.model.task.StartDate;

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

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new StartDate(startDate));
        return this;
    }

    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(new EndDate(endDate));
        return this;
    }

    public TaskBuilder withRecurring(String recur) throws IllegalValueException {
        this.task.setRecur(new Recurring(recur));
        return this;
    }

    public TaskBuilder withPriority(char c) throws IllegalValueException {
        this.task.setPriority(new Priority(String.valueOf(c)));
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        this.task.initTaskType();
        return this.task;
    }

}
