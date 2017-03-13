package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.TaskManager;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager tache = new TaskManagerBuilder().withTask("Buy", "Eggs", "and", "Bread")
 *                                                        .withTag("HighPriority").build();}
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
