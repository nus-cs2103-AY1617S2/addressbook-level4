package onlythree.imanager.testutil;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.StartEndDateTime;

//@@author A0140023E
/**
 * A Task Builder class that allow us to construct a Task by chaining calls for convenience in testing.
 */
//@@author
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        task.setName(new Name(name));
        return this;
    }

    //@@author A0140023E
    public TaskBuilder withDeadline(Deadline deadline) throws IllegalValueException {
        task.setDeadline(deadline);
        return this;
    }

    public TaskBuilder withStartEndDateTime(StartEndDateTime startEndDateTime) throws IllegalValueException {
        task.setStartEndDateTime(startEndDateTime);
        return this;
    }

    //@@author
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return task;
    }

}
