package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Tag;
import seedu.taskboss.model.category.UniqueTagList;
import seedu.taskboss.model.task.Email;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;

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

    public TaskBuilder withInformation(String information) throws IllegalValueException {
        this.task.setInformation(new Information(information));
        return this;
    }

    public TaskBuilder withPriorityLevel(String priorityLevel) throws IllegalValueException {
        this.task.setPriorityLevel(new PriorityLevel(priorityLevel));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Email(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
