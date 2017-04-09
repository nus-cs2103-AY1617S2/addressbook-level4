package seedu.onetwodo.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.exceptions.DuplicateDataException;
import seedu.onetwodo.model.SortOrder;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    public void add(int idx, Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(idx, toAdd);
    }


    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
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
     * Marks the equivalent task as completed.
     *
     *
     */
    public void done(ReadOnlyTask taskToComplete) {
        int index = internalList.indexOf(taskToComplete);
        assert index >= 0;
        Task targetTask = (Task) taskToComplete;
        targetTask.setDone();
        internalList.set(index, targetTask);
    }

    /**
     * Marks the equivalent task as uncompleted.
     *
     *
     */
    public void undone(ReadOnlyTask taskToUncomplete) {
        int index = internalList.indexOf(taskToUncomplete);
        assert index >= 0;
        Task targetTask = (Task) taskToUncomplete;
        targetTask.setUndone();
        internalList.set(index, targetTask);
    }

    //@@author A0135739W
    /**
     * clears completed tasks.
     *
     *
     */
    public void clearDone() {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getDoneStatus() == true) {
                internalList.remove(internalList.get(i));
                i--;
            }
        }
    }

    //@@author A0135739W
    /**
     * clears completed tasks.
     *
     *
     */
    public void clearUndone() {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getDoneStatus() == false) {
                internalList.remove(internalList.get(i));
                i--;
            }
        }
    }

    //@@author A0141138N
    /**
     * Marks the equivalent task as task for today.
     *
     *
     */
    public void today(ReadOnlyTask taskForToday) {
        int index = internalList.indexOf(taskForToday);
        assert index >= 0;
        Task targetTask = (Task) taskForToday;
        targetTask.setToday();
        internalList.set(index, targetTask);
    }

    //@@author A0143029M
    public void sortTasks(SortOrder sortOrder, boolean isReversed) {
        switch (sortOrder) {
        case ALPHANUMERIC:
            if (!isReversed) {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t1.getName().compareTo(t2.getName()));
            } else {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t2.getName().compareTo(t1.getName()));
            }
            break;
        case PRIORITY:
            if (!isReversed) {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t1.getPriority().compareTo(t2.getPriority()));
            } else {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t2.getPriority().compareTo(t1.getPriority()));
            }
            break;
        case DATETIME:
            if (!isReversed) {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t1.compareTo(t2));
            } else {
                FXCollections.sort(internalList, (Task t1, Task t2) -> t2.compareTo(t1));
            }
            break;
        default:
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
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

}
