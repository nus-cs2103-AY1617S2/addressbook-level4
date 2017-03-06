package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ToDoList addressBook;

    public AddressBookBuilder(ToDoList addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public ToDoList build() {
        return addressBook;
    }
}
