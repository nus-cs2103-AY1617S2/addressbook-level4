package typetask.testutil;

import typetask.model.TaskManager;
import typetask.model.task.Task;

/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager tm = new TaskManagerBuilder().withTask("John", "Doe").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) {
        taskManager.addTask(task);
        return this;
    }


    public TaskManager build() {
        return taskManager;
    }
}
