package seedu.taskList.testutil;

import seedu.taskList.commons.exceptions.IllegalValueException;
import seedu.taskList.model.TaskList;
import seedu.taskList.model.tag.Tag;
import seedu.taskList.model.task.Task;
import seedu.taskList.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList taskList) {
        this.taskList = taskList;
    }

    public TaskListBuilder withTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(person);
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
