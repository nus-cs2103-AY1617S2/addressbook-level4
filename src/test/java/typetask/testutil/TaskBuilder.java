package typetask.testutil;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Priority;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    public TaskBuilder withCompleted(boolean isCompleted) {
        this.task.setIsCompleted(isCompleted);
        return this;
    }

    public TaskBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(new DueDate(date));
        return this;
    }
    public TaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setEndDate(new DueDate(date));
        return this;
    }
    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }
    public TestTask build() {
        return this.task;
    }

}
