//@@author A0144885R-reused
package seedu.address.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;

/**
 * A list of tasks.
 *
 * Supports a minimal set of list operations.
 */
public class TaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /** Get the task with given ID
    public Optional<Task> getTaskByID(IdentificationNumber ID) {
        for (Task task : internalList) {
            if (task.getID().equals(ID)) {
                return Optional.of(task);
            }
        }
        return Optional.ofNullable(null);
    }*/

    /** Adds a task to the list.  */
    public void add(Task toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated person.
        // The right way is to implement observable properties in the Person class.
        // Then, PersonCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean removeTask(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     * Remove task with given ID.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    /*
    public boolean removeById(IdentificationNumber ID) throws TaskNotFoundException {
        assert ID != null;
        final Optional<Task> toRemove = getTaskByID(ID);
        if (!toRemove.isPresent()) {
            throw new TaskNotFoundException();
        }
        return internalList.remove(toRemove);
    }*/

    public void setTasks(TaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        final TaskList replacement = new TaskList();
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
                || (other instanceof TaskList // instanceof handles nulls
                && this.internalList.equals(
                ((TaskList) other).internalList));
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
