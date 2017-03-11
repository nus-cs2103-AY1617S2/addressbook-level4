package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 * A utility class to help with building Taskmanager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        return this;
    }

/*    public TaskManagerBuilder withCategory(String categoryName) throws IllegalValueException {
        taskManager.addCategory(new Category(categoryName));
        return this;
    } */

    public TaskManager build() {
        return taskManager;
    }
}
