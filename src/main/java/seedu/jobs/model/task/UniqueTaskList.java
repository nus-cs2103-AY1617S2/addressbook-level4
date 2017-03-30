package seedu.jobs.model.task;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.commons.exceptions.DuplicateDataException;
import seedu.jobs.commons.util.CollectionUtil;
import seedu.jobs.model.FixedStack;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final FixedStack<ObservableList<Task>> undoStack = new FixedStack();
    private final FixedStack<ObservableList<Task>> redoStack = new FixedStack();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        ObservableList<Task> stackList = FXCollections.observableArrayList();
        for (Task t : internalList) {
            stackList.add(t);
        }
        undoStack.push(stackList);
        internalList.add(toAdd);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        ObservableList<Task> stackList = FXCollections.observableArrayList();
        Task temp;
        for (Task t : internalList) {
            temp = new Task(t);
            stackList.add(temp);
        }
        undoStack.push(stackList);

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated person.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        ObservableList<Task> stackList = FXCollections.observableArrayList();
        for (Task t : internalList) {
            stackList.add(t);
        }
        undoStack.push(stackList);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     * Completes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean complete(int index, ReadOnlyTask toComplete) {
        assert toComplete != null;

        ObservableList<Task> stackList = FXCollections.observableArrayList();
        for (Task t : internalList) {
            stackList.add(t);
        }
        undoStack.push(stackList);
        Task taskToComplete = internalList.get(index);
        taskToComplete.markComplete();
        internalList.set(index, taskToComplete);
        return true;
    }

    /**
     *
     * @param replacement
     */

    public void undo() throws EmptyStackException {
        ObservableList<Task> replacement = undoStack.pop();
        ObservableList<Task> redoTemp = FXCollections.observableArrayList();
        for (Task t : internalList) {
            redoTemp.add(t);
        }
        redoStack.push(redoTemp);
        this.internalList.setAll(replacement);
    }

    public void redo() throws EmptyStackException {
        ObservableList<Task> replacement = redoStack.pop();
        ObservableList<Task> undoTemp = FXCollections.observableArrayList();
        for (Task t : internalList) {
            undoTemp.add(t);
        }
        undoStack.push(undoTemp);
        this.internalList.setAll(replacement);
    }

    public void setTasks(UniqueTaskList replacement) {

        ObservableList<Task> stackList = FXCollections.observableArrayList();
        for (Task t : internalList) {
            stackList.add(t);
        }
        undoStack.push(stackList);

        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();

        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Task> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

}
