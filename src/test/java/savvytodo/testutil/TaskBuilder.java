package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Address;
import savvytodo.model.task.Description;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;

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

    public TaskBuilder withAddress(String address) throws IllegalValueException {
        this.task.setAddress(new Address(address));
        return this;
    }

    public TaskBuilder withPriority(String phone) throws IllegalValueException {
        this.task.setPhone(new Priority(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Description(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
