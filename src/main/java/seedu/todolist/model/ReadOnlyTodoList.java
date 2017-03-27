package seedu.todolist.model;

import javafx.collections.ObservableList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * Unmodifiable view of a todo list
 */
public interface ReadOnlyTodoList {

    /**
     * Returns an unmodifiable view of the todo list.
     * This list will not contain any duplicate todos.
     */
    ObservableList<ReadOnlyTodo> getTodoList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
