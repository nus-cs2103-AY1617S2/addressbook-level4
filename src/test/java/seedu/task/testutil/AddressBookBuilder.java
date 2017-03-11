package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList addressBook;

    public AddressBookBuilder(TaskList addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build() {
        return addressBook;
    }
}
