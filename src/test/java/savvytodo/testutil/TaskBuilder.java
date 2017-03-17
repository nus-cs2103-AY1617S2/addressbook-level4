package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;

/**
 * @author A0140016B
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

    public TaskBuilder withCategories(String ... categories) throws IllegalValueException {
        task.setCategories(new UniqueCategoryList());
        for (String category: categories) {
            task.getCategories().add(new Category(category));
        }
        return this;
    }

    public TaskBuilder withLocation(String location) throws IllegalValueException {
        this.task.setLocation(new Location(location));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withDateTime(String start, String end) throws IllegalValueException {
        this.task.setDateTime(new DateTime(start, end));
        return this;
    }

    public TaskBuilder withRecurrence(String[] recurrence) throws IllegalValueException {
        this.task.setRecurrence(new Recurrence(recurrence));
        return this;
    }

    public TaskBuilder withStatus(boolean isCompleted) throws IllegalValueException {
        this.task.setCompleted(new Status(isCompleted));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
