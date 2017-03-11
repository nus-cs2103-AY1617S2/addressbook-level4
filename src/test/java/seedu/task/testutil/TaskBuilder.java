package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;

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

    public TaskBuilder withLocation(String location) throws IllegalValueException {
        this.task.setLocation(new Location(location));
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(new Date(startDate));
        return this;
    }

    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(new Date(endDate));
        return this;
    }

    public TaskBuilder withRemark(String remark) throws IllegalValueException {
        this.task.setRemark(new Remark(remark));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
