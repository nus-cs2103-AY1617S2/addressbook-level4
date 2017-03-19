package seedu.taskboss.model.task;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.DuplicateDataException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.commons.util.CollectionUtil;

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

    public enum SortBy {
        START_DATE_TIME, END_DATE_TIME
    }

    public void sort(SortBy sortType) throws IllegalValueException {
        Comparator<ReadOnlyTask> taskCmp = null;
        switch(sortType) {
            case START_DATE_TIME:
                taskCmp =  new Comparator<ReadOnlyTask>() {
                    @Override
                    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
                        Date startDateTime1 = o1.getStartDateTime().getDate();
                        Date startDateTime2 = o2.getStartDateTime().getDate();
                        if (startDateTime1 == null &&
                            startDateTime2 == null) {
                                return 0;
                        } else if (startDateTime1 == null) {
                            return 1;
                        } else if (startDateTime2 == null) {
                            return -1;
                        } else {
                            return o1.getStartDateTime().getDate()
                                .compareTo(o2.getStartDateTime().getDate());
                        }
                    }
                };
                break;

            case END_DATE_TIME:
                taskCmp = new Comparator<ReadOnlyTask> () {
                    @Override
                    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
                        Date endDateTime1 = o1.getEndDateTime().getDate();
                        Date endDateTime2 = o2.getEndDateTime().getDate();
                        if (endDateTime1 == null &&
                            endDateTime2 == null) {
                                return 0;
                        } else if (endDateTime1 == null) {
                            return 1;
                        } else if (endDateTime2 == null) {
                            return -1;
                        } else {
                            return o1.getEndDateTime().getDate()
                                .compareTo(o2.getEndDateTime().getDate());
                        }
                    }
                };
                break;

            default:
                throw new IllegalValueException("Invalid sorting type.");
        }

        FXCollections.sort(internalList, taskCmp);
    }

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
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
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
