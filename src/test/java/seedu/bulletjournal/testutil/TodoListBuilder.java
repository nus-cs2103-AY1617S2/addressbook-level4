package seedu.bulletjournal.testutil;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.UniqueTaskList;

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
        todoList.addTask(task);
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
