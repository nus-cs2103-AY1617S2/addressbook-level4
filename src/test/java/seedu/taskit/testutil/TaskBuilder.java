package seedu.taskit.testutil;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Title;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.Date;

/**
 *
 */
public class TaskBuilder {
    //@@author A0141011J
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

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }
    
    // @@author A0163996J

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }
    
    public TaskBuilder withStart(String start) throws IllegalValueException {
        this.task.setStart(new Date(start));
        return this;
    }
    
    public TaskBuilder withEnd(String end) throws IllegalValueException {
        this.task.setEnd(new Date(end));
        return this;
    }

    //@@author A0141011J

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    //@@author A0097141H
    public TaskBuilder withDone(String done) throws IllegalValueException {
    	task.setDone(done);
    	return this;
    }
    
    public TaskBuilder withEnd(Date end) throws IllegalValueException {
    	task.setEnd(end);
    	return this;
    }
    
    public TaskBuilder withStart(Date start) throws IllegalValueException {
    	task.setStart(start);
    	return this;
    }

    public TestTask build() {
        return this.task;
    }

}
