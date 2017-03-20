//@@author A0139221N
package seedu.tasklist.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;

public class EventTaskBuilder extends TaskBuilder {
    private TestEventTask task;

    /**
     * Creates a event task builder
     */
    public EventTaskBuilder() {
        this.task = new TestEventTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public EventTaskBuilder(TestEventTask taskToCopy) {
        this.task = new TestEventTask(taskToCopy);
    }

    /**
     * Sets the name of the task.
     */
    public EventTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    /**
     * Sets the tags of the task.
     */
    public EventTaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    /**
     * Sets the comment of the task.
     */
    public EventTaskBuilder withComment(String comment) throws IllegalValueException {
        this.task.setComment(new Comment(comment));
        return this;
    }

    /**
     * Returns the task built.
     */
    public TestTask build() {
        return this.task;
    }

    /**
     * Sets the status of the task.
     */
    @Override
    public EventTaskBuilder withStatus(Boolean completed) throws IllegalValueException {
        this.task.setStatus(new Status(completed));
        return this;
    }

    /**
     * Sets the priority of the task.
     */
    @Override
    public EventTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the start date of the event task, in the format dd/MM/yyyy HH:mm:ss
     */
    public EventTaskBuilder withStartDate(String date) throws IllegalValueException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.task.setStartDate(dateFormat.parse(date));
        return this;
    }

    /**
     * Sets the end date of the event task, in the format dd/MM/yyyy HH:mm:ss
     */
    public EventTaskBuilder withEndDate(String date) throws IllegalValueException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.task.setEndDate(dateFormat.parse(date));
        return this;
    }
}
