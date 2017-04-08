package onlythree.imanager.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import onlythree.imanager.commons.core.UnmodifiableObservableList;
import onlythree.imanager.commons.exceptions.IllegalValueException;

/**
 * A list of tasks that does not allow nulls that can be iterated.
 *
 * Supports a minimal set of list operations.
 *
 */
public class IterableTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    //@@author A0140023E
    /**
     * Adds a task to the end of the list and returns the index where the task is added.
     */
    public int add(Task toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);

        return internalList.size() - 1;
    }

    //@@author
    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
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
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(IterableTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        final IterableTaskList replacement = new IterableTaskList();
        for (final ReadOnlyTask task : tasks) {
            try {
                replacement.add(new Task(task));
            } catch (IllegalValueException e) {
                throw new AssertionError("Copying a valid task should always result in a valid task");
            }
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
                || (other instanceof IterableTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((IterableTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

}
