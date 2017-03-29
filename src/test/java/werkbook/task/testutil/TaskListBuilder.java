package werkbook.task.testutil;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.model.TaskList;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList tl = new TaskListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList taskList) {
        this.taskList = taskList;
    }

    public TaskListBuilder withTask(Task task) throws DuplicateTaskException {
        taskList.addTask(task);
        return this;
    }

    public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build() {
        return taskList;
    }
}
