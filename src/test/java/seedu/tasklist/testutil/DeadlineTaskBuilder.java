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

public class DeadlineTaskBuilder extends TaskBuilder {
    private TestDeadlineTask task;

    /**
     * Creates a deadline task builder with a new deadline task,
     */
    public DeadlineTaskBuilder() {
        this.task = new TestDeadlineTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public DeadlineTaskBuilder(TestDeadlineTask taskToCopy) {
        this.task = new TestDeadlineTask(taskToCopy);
    }

    /**
     * Sets the name of the task.
     */
    public DeadlineTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    /**
     * Sets the tags of the task.
     */
    public DeadlineTaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    /**
     * Sets the comment of the task.
     */
    public DeadlineTaskBuilder withComment(String comment) throws IllegalValueException {
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
    public DeadlineTaskBuilder withStatus(Boolean completed) throws IllegalValueException {
        this.task.setStatus(new Status(completed));
        return this;
    }

    /**
     * Sets the priority of the task.
     */
    @Override
    public DeadlineTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the deadline of the deadline task, in the format dd/MM/yyyy HH:mm:ss
     */
    public DeadlineTaskBuilder withDeadline(String date) throws IllegalValueException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.task.setDeadline(dateFormat.parse(date));
        return this;
    }
}
