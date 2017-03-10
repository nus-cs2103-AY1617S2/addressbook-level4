package t15b1.taskcrusher.testutil;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.UserInbox;
import t15b1.taskcrusher.model.tag.Tag;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private UserInbox addressBook;

    public AddressBookBuilder(UserInbox addressBook) {
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

    public UserInbox build() {
        return addressBook;
    }
}
