package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class EzDoBuilder {

    private EzDo ezDo;

    public EzDoBuilder(EzDo ezDo) {
        this.ezDo = ezDo;
    }

    public EzDoBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        ezDo.addTask(task);
        return this;
    }

    public EzDoBuilder withTag(String tagName) throws IllegalValueException {
        ezDo.addTag(new Tag(tagName));
        return this;
    }

    public EzDo build() {
        return ezDo;
    }
}
