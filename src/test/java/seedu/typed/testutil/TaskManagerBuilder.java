package seedu.typed.testutil;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.TaskManager;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskManager objects. Example usage:
 * <br>
 * {@code TaskManager tm = new TaskManagerBuilder().withTask("John", "Doe").withTag("Friend").build();}
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

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return taskManager;
    }
}
