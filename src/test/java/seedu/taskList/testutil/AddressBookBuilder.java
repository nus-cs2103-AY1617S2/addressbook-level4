package seedu.taskList.testutil;

import seedu.taskList.commons.exceptions.IllegalValueException;
import seedu.taskList.model.TaskList;
import seedu.taskList.model.tag.Tag;
import seedu.taskList.model.task.Person;
import seedu.taskList.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList taskList;

    public AddressBookBuilder(TaskList taskList) {
        this.taskList = taskList;
    }

    public AddressBookBuilder withPerson(Person person) throws UniqueTaskList.DuplicatePersonException {
        taskList.addPerson(person);
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
