package seedu.jobs.testutil;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.task.Person;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskBook taskManager;

    public TaskManagerBuilder(TaskBook taskManager) {
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

    public TaskBook build() {
        return taskManager;
    }
}
