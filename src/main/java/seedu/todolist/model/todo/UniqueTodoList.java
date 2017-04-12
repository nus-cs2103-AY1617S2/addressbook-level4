package seedu.todolist.model.todo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.DuplicateDataException;
import seedu.todolist.commons.util.CollectionUtil;

/**
 * A list of todos that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Todo#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTodoList implements Iterable<Todo> {

    private final ObservableList<Todo> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent todo as the given argument.
     */
    public boolean contains(ReadOnlyTodo toAdd) {
        assert toAdd != null;
        return internalList.contains(toAdd);
    }

    /**
     * Adds a todo to the list.
     *
     * @throws DuplicateTodoException if the todo to add is a duplicate of an existing todo in the list.
     */
    public void add(Todo toAdd) throws DuplicateTodoException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTodoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the todo in the list at position {@code index} with {@code editedTodo}.
     *
     * @throws DuplicateTodoException if updating the todo's details causes the todo to be equivalent to
     *      another existing todo in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTodo(int index, ReadOnlyTodo editedTodo) throws DuplicateTodoException {
        assert editedTodo != null;

        Todo todoToUpdate = internalList.get(index);
        if (!todoToUpdate.equals(editedTodo) && internalList.contains(editedTodo)) {
            throw new DuplicateTodoException();
        }

        todoToUpdate.resetData(editedTodo);
        // TODO: The code below is just a workaround to notify observers of the updated todo.
        // The right way is to implement observable properties in the Todo class.
        // Then, TodoCard should then bind its text labels to those observable properties.
        internalList.set(index, todoToUpdate);
    }
    //@@author A0163786N
    /**
     * Completes the todo in the list at position {@code index} with {@code completeTime}
     */
    public void completeTodo(int index, Date completeTime) {
        Todo todoToComplete = internalList.get(index);
        todoToComplete.setCompleteTime(completeTime);
        internalList.set(index, todoToComplete);
    }
    //@@author
    //@@author A0163786N
    /**
     * Uncompletes the todo in the list at position {@code index}
     */
    public void uncompleteTodo(int index) {
        Todo todoToUncomplete = internalList.get(index);
        todoToUncomplete.setCompleteTime(null);
        internalList.set(index, todoToUncomplete);
    }
    //@@author
    /**
     * Removes the equivalent todo from the list.
     *
     * @throws TodoNotFoundException if no such todo could be found in the list.
     */
    public boolean remove(ReadOnlyTodo toRemove) throws TodoNotFoundException {
        assert toRemove != null;
        final boolean todoFoundAndDeleted = internalList.remove(toRemove);
        if (!todoFoundAndDeleted) {
            throw new TodoNotFoundException();
        }
        return todoFoundAndDeleted;
    }

    public void setTodos(UniqueTodoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTodos(List<? extends ReadOnlyTodo> todos) throws DuplicateTodoException {
        final UniqueTodoList replacement = new UniqueTodoList();
        for (final ReadOnlyTodo todo : todos) {
            replacement.add(new Todo(todo));
        }
        setTodos(replacement);
    }

    /**
     * Returns todo from list of todos via index
     * @throws TodoNotFoundException
     */
    //@@author A0163720M
    public Todo getTodo(int index) throws TodoNotFoundException {
        if (index < 0 || index >= this.internalList.size()) {
            throw new TodoNotFoundException();
        }

        return this.internalList.get(index);
    }
    //@@author

    public UnmodifiableObservableList<Todo> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Todo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTodoList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTodoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTodoException extends DuplicateDataException {
        protected DuplicateTodoException() {
            super("Operation would result in duplicate todo");
        }
    }

    /**
     * Signals that an operation targeting a specified todo in the list would fail because
     * there is no such matching todo in the list.
     */
    public static class TodoNotFoundException extends Exception {}

}
