package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoApp;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building ToDoApp objects.
 * Example usage: <br>
 *     {@code ToDoApp ab = new ToDoAppBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class ToDoAppBuilder {

    private ToDoApp toDoApp;

    public ToDoAppBuilder(ToDoApp toDoApp) {
        this.toDoApp = toDoApp;
    }

    public ToDoAppBuilder withTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        toDoApp.addTask(person);
        return this;
    }

    public ToDoAppBuilder withTag(String tagName) throws IllegalValueException {
        toDoApp.addTag(new Tag(tagName));
        return this;
    }

    public ToDoApp build() {
        return toDoApp;
    }
}
