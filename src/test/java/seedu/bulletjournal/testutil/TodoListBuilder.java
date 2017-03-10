package seedu.bulletjournal.testutil;

import seedu.bullletjournal.commons.exceptions.IllegalValueException;
import seedu.bullletjournal.model.TodoList;
import seedu.bullletjournal.model.tag.Tag;
import seedu.bullletjournal.model.task.Task;
import seedu.bullletjournal.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList todoList;

    public TodoListBuilder(TodoList todoList) {
        this.todoList = todoList;
    }

    public TodoListBuilder withPerson(Task task) throws UniqueTaskList.DuplicatePersonException {
        todoList.addPerson(task);
        return this;
    }

    public TodoListBuilder withTag(String tagName) throws IllegalValueException {
        todoList.addTag(new Tag(tagName));
        return this;
    }

    public TodoList build() {
        return todoList;
    }
}
