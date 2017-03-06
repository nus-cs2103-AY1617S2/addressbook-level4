package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList taskList;

    public AddressBookBuilder(TaskList addressBook) {
        this.taskList = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build() {
        return taskList;
    }
}
