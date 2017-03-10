package seedu.onetwodo.testutil;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.person.Task;
import seedu.onetwodo.model.person.UniqueTaskList;
import seedu.onetwodo.model.tag.Tag;

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
