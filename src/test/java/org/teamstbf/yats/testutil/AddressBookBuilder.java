package org.teamstbf.yats.testutil;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.Task;
import org.teamstbf.yats.model.item.UniqueItemList;
import org.teamstbf.yats.model.tag.Tag;

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

    public AddressBookBuilder withPerson(Event person) throws UniqueItemList.DuplicatePersonException {
        addressBook.addEvent(person);
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
