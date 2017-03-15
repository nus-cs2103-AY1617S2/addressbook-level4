package seedu.tache.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.commons.exceptions.DuplicateDataException;

public class UniqueDetailedTaskList implements Iterable<DetailedTask> {

    private final ObservableList<DetailedTask> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent detailed task as the given argument.
     */
    public boolean contains(ReadOnlyDetailedTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a detailed task to the list.
     *
     * @throws DuplicateDetailedTaskException if the detailed task to add is a duplicate of an
     *         existing detailed task in the list.
     */
    public void add(DetailedTask toAdd) throws DuplicateDetailedTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateDetailedTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the detailed task in the list at position {@code index} with {@code editedTask}.
     *
     * @throws DuplicateDetailedTaskException if updating the detailed task's details causes the
     *         detailed task to be equivalent to another existing detailed task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateDetailedTask(int index, ReadOnlyDetailedTask editedDetailedTask)
                                   throws DuplicateDetailedTaskException {
        assert editedDetailedTask != null;

        DetailedTask taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedDetailedTask) && internalList.contains(editedDetailedTask)) {
            throw new DuplicateDetailedTaskException();
        }

        taskToUpdate.resetData(editedDetailedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws DetailedTaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyDetailedTask toRemove) throws DetailedTaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new DetailedTaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setDetailedTasks(UniqueDetailedTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setDetailedTasks(List<? extends ReadOnlyDetailedTask> detailedTasks)
            throws DuplicateDetailedTaskException {
        final UniqueDetailedTaskList replacement = new UniqueDetailedTaskList();
        for (final ReadOnlyDetailedTask detailedTask : detailedTasks) {
            replacement.add(new DetailedTask(detailedTask));
        }
        setDetailedTasks(replacement);
    }

    public UnmodifiableObservableList<DetailedTask> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<DetailedTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDetailedTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueDetailedTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateDetailedTaskException extends DuplicateDataException {
        protected DuplicateDetailedTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified detailed task in the list would fail because
     * there is no such matching detailed task in the list.
     */
    public static class DetailedTaskNotFoundException extends Exception {}

}
