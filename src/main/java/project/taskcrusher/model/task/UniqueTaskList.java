package project.taskcrusher.model.task;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.exceptions.DuplicateDataException;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.logic.commands.MarkCommand;
//@@author A0127737X
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * At any point in time, {@code internalList} is sorted by the deadline.
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    public void sortTasksByDeadline () {
        internalList.sort(null);
    }

    /**
     *  Checks and marks all active, incomplete tasks whose deadline is past tehe current time.
     *  Returns boolean {@code isAnyUpdate} that indicates that whether or not anything was newly marked as overdue,
     *  which can be used by the caller to tell if there is a need to overwrite data in hard disk.
     */
    public boolean updateOverdueStatus() {
        boolean isAnyUpdate = false;
        Date now = new Date();
        for (Task task: internalList) {
            if (!task.isComplete() && !task.isOverdue()) {
                isAnyUpdate = task.updateOverdueStatus(now) || isAnyUpdate;
            }
        }
        return isAnyUpdate;
    }

    /**
     * Marks the task specified by {@code targetIndex} according to {@code markFlag}.
     */
    public void markTask(int targetIndex, int markFlag) {
        Task target = internalList.get(targetIndex);
        if (markFlag == MarkCommand.MARK_COMPLETE) {
            target.markComplete();
        } else if (markFlag == MarkCommand.MARK_INCOMPLETE) {
            target.markIncomplete();
        } else {
            assert false;
        }
        sortTasksByDeadline();
    }

//@@author
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
        sortTasksByDeadline();
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        internalList.set(index, taskToUpdate);
        sortTasksByDeadline();
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        sortTasksByDeadline();
        return taskFoundAndDeleted;

    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
        sortTasksByDeadline();
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
        sortTasksByDeadline();
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
    public static class TaskNotFoundException extends Exception {
        protected TaskNotFoundException() {
            super("The specified task was not found in the list");
        }
    }

}
