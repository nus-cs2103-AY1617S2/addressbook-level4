package seedu.doit.model.item;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.commons.exceptions.DuplicateDataException;
import seedu.doit.commons.util.CollectionUtil;

/**
 * A list of floatingTasks that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see FloatingTask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueFloatingTaskList implements Iterable<FloatingTask> {

    private final ObservableList<FloatingTask> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent floatingTask as the given argument.
     */
    public boolean contains(ReadOnlyFloatingTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a floatingTask to the list.
     *
     * @throws DuplicateFloatingTaskException if the floatingTask to add is a duplicate
     *         of an existing floatingTask in the list.
     */
    public void add(FloatingTask toAdd) throws DuplicateFloatingTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateFloatingTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the floatingTask in the list at position {@code index} with {@code editedFloatingTask}.
     *
     * @throws DuplicateFloatingTaskException if updating the floatingTask's details
     *         causes the floatingTask to be equivalent to
     *                                   another existing floatingTask in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateFloatingTask(int index, ReadOnlyFloatingTask editedFloatingTask)
        throws DuplicateFloatingTaskException {
        assert editedFloatingTask != null;

        FloatingTask floatingTaskToUpdate = internalList.get(index);
        if (!floatingTaskToUpdate.equals(editedFloatingTask) && internalList.contains(editedFloatingTask)) {
            throw new DuplicateFloatingTaskException();
        }

        floatingTaskToUpdate.resetData(editedFloatingTask);
        // TODO: The code below is just a workaround to notify observers of the updated floatingTask.
        // The right way is to implement observable properties in the FloatingTask class.
        // Then, FloatingTaskCard should then bind its text labels to those observable properties.
        internalList.set(index, floatingTaskToUpdate);
    }

    /**
     * Removes the equivalent floatingTask from the list.
     *
     * @throws FloatingTaskNotFoundException if no such floatingTask could be found in the list.
     */
    public boolean remove(ReadOnlyFloatingTask toRemove) throws FloatingTaskNotFoundException {
        assert toRemove != null;
        final boolean floatingTaskFoundAndDeleted = internalList.remove(toRemove);
        if (!floatingTaskFoundAndDeleted) {
            throw new FloatingTaskNotFoundException();
        }
        return floatingTaskFoundAndDeleted;
    }

    public void setFloatingTasks(UniqueFloatingTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setFloatingTasks(List<? extends ReadOnlyFloatingTask> floatingTasks)
        throws DuplicateFloatingTaskException {
        final UniqueFloatingTaskList replacement = new UniqueFloatingTaskList();
        for (final ReadOnlyFloatingTask floatingTask : floatingTasks) {
            replacement.add(new FloatingTask(floatingTask));
        }
        setFloatingTasks(replacement);
    }

    public UnmodifiableObservableList<FloatingTask> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<FloatingTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueFloatingTaskList
            && this.internalList.equals(
            ((UniqueFloatingTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateFloatingTaskException extends DuplicateDataException {
        protected DuplicateFloatingTaskException() {
            super("Operation would result in duplicate floatingTasks");
        }
    }

    /**
     * Signals that an operation targeting a specified floatingTask in the list would fail because
     * there is no such matching floatingTask in the list.
     */
    public static class FloatingTaskNotFoundException extends Exception {
    }

}
