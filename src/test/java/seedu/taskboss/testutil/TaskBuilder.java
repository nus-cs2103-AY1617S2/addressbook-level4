package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;

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

    public TaskBuilder withCategories(String ... categories) throws IllegalValueException {
        task.setCategories(new UniqueCategoryList());
        for (String category: categories) {
            task.getCategories().add(new Category(category));
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

    public TaskBuilder withStartDateTime(String startDateTime) throws IllegalValueException {
        this.task.setStartDateTime((new DateTime(startDateTime)));
        return this;
    }

    public TaskBuilder withEndDateTime(String endDateTime) throws IllegalValueException {
        this.task.setEndDateTime(new DateTime(endDateTime));
        return this;
    }

    public TaskBuilder withRecurrence(Frequency frequency) throws IllegalArgumentException {
        this.task.setRecurrence(new Recurrence(frequency));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
