package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building ToDoList objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new ToDoListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList toDoList;

    public ToDoListBuilder(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    public ToDoListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        return this;
    }

    public ToDoListBuilder withTag(String tagName) throws IllegalValueException {
        toDoList.addTag(new Tag(tagName));
        return this;
    }

    public ToDoList build() {
        return toDoList;
    }
}
