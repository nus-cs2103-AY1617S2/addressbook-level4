package seedu.todolist.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.TodoList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList;

/**
 * A utility class to help with building TodoList objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withTodo("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList todoList;

    public TodoListBuilder(TodoList todoList) {
        this.todoList = todoList;
    }

    public TodoListBuilder withTodo(Todo todo) throws UniqueTodoList.DuplicateTodoException {
        todoList.addTodo(todo);
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
