package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.label.Label;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        return this;
    }

    public AddressBookBuilder withLabel(String labelName) throws IllegalValueException {
        addressBook.addLabel(new Label(labelName));
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
