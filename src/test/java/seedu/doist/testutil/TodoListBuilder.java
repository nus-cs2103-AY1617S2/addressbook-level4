package seedu.doist.testutil;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList todoList;

    public TodoListBuilder(TodoList addressBook) {
        this.todoList = addressBook;
    }

    public TodoListBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(person);
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
