// @@author A0139399J
package seedu.doit.model.item;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.exceptions.DuplicateDataException;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.model.comparators.TaskNameComparator;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private Comparator<ReadOnlyTask> taskComparator = new TaskNameComparator();

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
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        internalList.sort(taskComparator);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws DuplicateTaskException    if updating the task's details causes the task to be equivalent to
     *                                   another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
        internalList.sort(taskComparator);
    }

    /**
     * Marks the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException
     */
    public void mark(int taskIndex, ReadOnlyTask toMark) throws TaskNotFoundException, DuplicateTaskException {
        assert toMark != null;
        Task markedTask = new Task(toMark);
        markedTask.setIsDone(true);
        updateTask(taskIndex, markedTask);
        internalList.sort(taskComparator);
    }

    /**
     * Marks the equivalent task in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException
     */
    public void unmark(int taskIndex, ReadOnlyTask toMark) throws TaskNotFoundException, DuplicateTaskException {
        assert toMark != null;
        Task markedTask = new Task(toMark);
        markedTask.setIsDone(false);
        updateTask(taskIndex, markedTask);
        internalList.sort(taskComparator);
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
        internalList.sort(taskComparator);
        return taskFoundAndDeleted;
    }

    /**
     * Removes the equivalent tasks from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Set<ReadOnlyTask> tasksToRemove) throws TaskNotFoundException {
        assert tasksToRemove != null;
        final boolean taskFoundAndDeleted = internalList.removeAll(tasksToRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        internalList.sort(taskComparator);
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
        internalList.sort(taskComparator);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }


    /**
     * Sets the comparator by which tasks from the list will be sorted by.
     */
    public void setTaskComparator(Comparator<ReadOnlyTask> taskComparator) {
        this.taskComparator = taskComparator;
        internalList.sort(taskComparator);
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
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

}
