package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;
import seedu.address.model.todo.UniqueTodoList.DuplicateTodoException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTodoList newData);

    /** Returns the TodoList */
    ReadOnlyTodoList getTodoList();

    /** Deletes the given todo. */
    void deleteTodo(ReadOnlyTodo target) throws UniqueTodoList.TodoNotFoundException;

    /** Adds the given todo */
    void addTodo(Todo todo) throws UniqueTodoList.DuplicateTodoException;

    /**
     * Updates the todo located at {@code filteredTodoListIndex} with {@code editedTodo}.
     *
     * @throws DuplicateTodoException if updating the todo's details causes the todo to be equivalent to
     *      another existing todo in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTodoListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTodo(int filteredTodoListIndex, ReadOnlyTodo editedTodo)
            throws UniqueTodoList.DuplicateTodoException;

    /** Returns the filtered todo list as an {@code UnmodifiableObservableList<ReadOnlyTodo>} */
    UnmodifiableObservableList<ReadOnlyTodo> getFilteredTodoList();

    /** Updates the filter of the filtered todo list to show all todos */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered todo list to filter by the given keywords*/
    void updateFilteredTodoList(Set<String> keywords);

    /** Loads the previous state of the todo list*/
	void loadPreviousState() throws NoPreviousStateException;

	/**
	 * Signals that there is no previous todo state to load
	 */
	public static class NoPreviousStateException extends Exception {}

}
