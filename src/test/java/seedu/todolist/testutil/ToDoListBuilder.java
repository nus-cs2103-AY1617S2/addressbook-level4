package seedu.todolist.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;

/**
 * A utility class to help with building ToDoList objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList toDoList;

    public ToDoListBuilder(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    public ToDoListBuilder withPerson(Task task) throws UniqueTaskList.DuplicateTaskException {
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
