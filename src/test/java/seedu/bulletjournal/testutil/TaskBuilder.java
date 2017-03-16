package seedu.bulletjournal.testutil;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.Deadline;
import seedu.bulletjournal.model.task.Detail;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask personToCopy) {
        this.task = new TestTask(personToCopy);
    }

    public TaskBuilder withTaskName(String taskName) throws IllegalValueException {
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

    public TaskBuilder withDetail(String address) throws IllegalValueException {
        this.task.setAddress(new Detail(address));
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setPhone(new Deadline(deadline));
        return this;
    }

    public TaskBuilder withStatus(String email) throws IllegalValueException {
        this.task.setEmail(new Status(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
