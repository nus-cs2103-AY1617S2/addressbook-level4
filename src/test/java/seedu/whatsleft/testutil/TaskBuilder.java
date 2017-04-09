package seedu.whatsleft.testutil;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.model.tag.UniqueTagList;

/**
 * Task builder for GUI test
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

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withByTime(String byTime) throws IllegalValueException {
        this.task.setByTime(new ByTime(byTime));
        return this;
    }

    public TaskBuilder withByDate(String byDate) throws IllegalValueException {
        this.task.setByDate(new ByDate(byDate));
        return this;
    }

    public TaskBuilder withLocation(String location) throws IllegalValueException {
        this.task.setLocation(new Location(location));
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
        return this.task;
    }

}
