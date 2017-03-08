package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaskList ab = new TaskListBuilder().withTask("Buy milk and eggs").withTag("important").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList addressBook) {
        this.taskList = addressBook;
    }

    public TaskListBuilder withPerson(Task task) throws UniqueTaskList.DuplicateTaskException {
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
