package seedu.jobs.testutil;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.AddressBook;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.task.Person;
import seedu.jobs.model.task.UniqueTaskList;

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

    public AddressBookBuilder withPerson(Person task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addPerson(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
