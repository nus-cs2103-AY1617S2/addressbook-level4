package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.TaskManager;
import savvytodo.model.category.Category;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskManager addressBook;

    public AddressBookBuilder(TaskManager addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addCategory(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addCategory(new Category(tagName));
        return this;
    }

    public TaskManager build() {
        return addressBook;
    }
}
