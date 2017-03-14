package seedu.doist.model.task;


import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.commons.exceptions.DuplicateDataException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class UniqueTaskList implements Iterable<Task> {

    private static final Logger logger = LogsCenter.getLogger(UniqueTaskList.class);
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Sorts the internal list with comparator
     */
    public void sort(Comparator<ReadOnlyTask> comparator) {
        assert comparator != null;
        internalList.sort(comparator);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public boolean add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        return true;
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     * @returns true if task is successfully updated
     */
    public boolean updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // ALERT: check this out? how to bind text labels?
        // TODO: The code below is just a workaround to notify observers of the updated person.
        // The right way is to implement observable properties in the Person class.
        // Then, PersonCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
        return true;
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

    /**
     * Changes the finish status of the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws TaskAlreadyFinishedException if task is already finished but trying to finish it
     * @throws TaskAlreadyUnfinishedException if task is already not finished but trying to unfinish it
     */
    public boolean changeFinishStatus(ReadOnlyTask toChangeFinish, boolean isToFinish) throws TaskNotFoundException,
            TaskAlreadyFinishedException, TaskAlreadyUnfinishedException {
        assert toChangeFinish != null;
        final int taskIndex = internalList.indexOf(toChangeFinish);
        boolean taskExists = taskIndex < 0 ? false : true;
        if (!taskExists) {
            throw new TaskNotFoundException();
        } else {
            Task task = internalList.get(taskIndex);
            if (isToFinish) {
                finishTask(task);
            } else {
                unfinishTask(task);
            }
            // the following line is to trigger person list view to update
            // according to the api:
            // All changes in the ObservableList are propagated immediately to the FilteredList.
            // without the following line, only the task is changed, but the list is not
            // so the person list view will not be updated, the isFinished field will remain "false"
            internalList.set(taskIndex, task);
        }
        return taskExists;
    }

    private void finishTask(Task toFinish) throws TaskAlreadyFinishedException {
        if (toFinish.getFinishedStatus().getIsFinished()) {
            logger.info("Attemping to finish task already finished, task details:\n" + toFinish.getAsText());
            throw new TaskAlreadyFinishedException();
        } else {
            toFinish.setFinishedStatus(true);
        }
    }

    private void unfinishTask(Task toUnfinish) throws TaskAlreadyUnfinishedException {
        if (!toUnfinish.getFinishedStatus().getIsFinished()) {
            logger.info("Attemping to unfinish task that is already not finished, task details:\n"
                    + toUnfinish.getAsText());
            throw new TaskAlreadyUnfinishedException();
        } else {
            toUnfinish.setFinishedStatus(false);
        }
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
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    /**
     * Signals that a task is already finished and you are trying to finish it again
     */
    public static class TaskAlreadyFinishedException extends Exception {}

    /**
     * Signals that a task is already not finished and you are trying to unfinish it
     */
    public static class TaskAlreadyUnfinishedException extends Exception {

    }

}
