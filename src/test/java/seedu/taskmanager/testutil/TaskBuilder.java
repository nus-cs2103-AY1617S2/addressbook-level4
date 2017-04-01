package seedu.taskmanager.testutil;

import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Title;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
        // @@author A0114269E
        this.task.setStatus(new Status()); // set to undone by default
        // @@author
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(Optional.ofNullable(new Description(description)));
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException {
        this.task.setStartDate(Optional.ofNullable(new StartDate(startDate)));
        return this;
    }

    public TaskBuilder withEndDate(String endDate) throws IllegalValueException {
        this.task.setEndDate(Optional.ofNullable(new EndDate(endDate)));
        return this;
    }
    
    // @@author A0114269E 
    /**
     * To allow building TestTask with done status 
     */
    public TaskBuilder withStatus(String status) {
        this.task.setStatus(new Status(status));
        return this;
    }
    // @@author A0114269E

    public TestTask build() {
        return this.task;
    }

}
