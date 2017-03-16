package seedu.jobs.testutil;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.AddressBook;
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

    private AddressBook addressBook;

    public TaskManagerBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
