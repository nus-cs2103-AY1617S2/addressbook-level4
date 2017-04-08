package seedu.taskit.testutil;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.TaskManager;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTask("do hw").withTag("school").build();}
 */
public class AddressBookBuilder {

    private TaskManager addressBook;

    public AddressBookBuilder(TaskManager addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return addressBook;
    }
}
