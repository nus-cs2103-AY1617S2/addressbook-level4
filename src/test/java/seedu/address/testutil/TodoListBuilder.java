package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TodoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withTodo("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList addressBook;

    public TodoListBuilder(TodoList addressBook) {
        this.addressBook = addressBook;
    }

    public TodoListBuilder withTodo(Todo todo) throws UniqueTodoList.DuplicateTodoException {
        addressBook.addTodo(todo);
        return this;
    }

    public TodoListBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TodoList build() {
        return addressBook;
    }
}
