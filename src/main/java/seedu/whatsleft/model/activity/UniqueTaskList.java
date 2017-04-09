package seedu.whatsleft.model.activity;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.exceptions.DuplicateDataException;
import seedu.whatsleft.commons.util.CollectionUtil;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    //@@author A0148038A
    /**
     * Clears all tasks in the task list
     */
    public void clearAll() {
        internalList.clear();
    }

    /**
     * Adds an Task to the task list.
     *
     * @param the task to add into the task list
     * @throws DuplicateTaskException if the Task to add is a duplicate of an existing Task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates(edits) a task in WhatsLeft.
     *
     * @param a task to edit and an edited task
     * @throws DuplicateTaskException if the edited task is a duplicate of an existing task in the list.
     */
    public void updateTask(Task taskToEdit, Task editedTask) throws UniqueTaskList.DuplicateTaskException {
        assert taskToEdit != null && editedTask != null;

        if (!taskToEdit.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        int index = internalList.indexOf(taskToEdit);
        taskToEdit.resetData(editedTask);
        internalList.set(index, editedTask);
        internalList.sorted();
    }

    //@@author A0121668A
    /**
     * Marks the task in the list at position {@code index} as complete.
     * @param task to mark as complete in ReadOnlyTask format
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void completeTask(ReadOnlyTask taskToMark) {
        Task taskToComplete = new Task(taskToMark);
        int index = internalList.indexOf(taskToComplete);
        taskToComplete.completeTask();
        internalList.set(index, taskToComplete);
    }

    /**
     * Marks the task in the list at position {@code index} as pending.
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void redoTask(ReadOnlyTask taskToMark) {
        Task taskToComplete = new Task(taskToMark);
        int index = internalList.indexOf(taskToComplete);
        taskToComplete.redoTask();
        internalList.set(index, taskToComplete);
    }
  //@@author

    /**
     * Removes the equivalent Task from the list.
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

    public void setTasks(UniqueTaskList replacement) {
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
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}
}
