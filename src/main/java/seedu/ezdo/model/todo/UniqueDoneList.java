package seedu.ezdo.model.todo;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;

/**
 * A list of done tasks and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueDoneList implements Iterable<ReadOnlyTask> {

    private final ObservableList<ReadOnlyTask> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the done list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(ReadOnlyTask toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);
    }
    
    public void setDone(UniqueDoneList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setDone(List<? extends ReadOnlyTask> tasks) {
        final UniqueDoneList replacement = new UniqueDoneList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setDone(replacement);
    }
    
    @Override
    public Iterator<ReadOnlyTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueDoneList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
