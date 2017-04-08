package onlythree.imanager.testutil;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList taskList = new TaskListBuilder().withTask("meeting", "project").withTag("important").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList taskList) {
        this.taskList = taskList;
    }

    public TaskListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
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
