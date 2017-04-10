package typetask.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.commons.util.CollectionUtil;

/**
 * A list of task that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class TaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);
    }
    /**
     * Refreshes the task list and sort them by deadlines
     * This is not unused code. For future implementation for sorting
     */
 //   public void refreshInternalList() {
  //      internalList.sort(new TaskComparator());
  //  }

    /**
     * Updates the task in the list at position {@code index} with {@code editedTask}.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedTask) {
        assert editedTask != null;

        Task taskToUpdate = internalList.get(index);

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    //@@author A0144902L
    /**
     * Marks the equivalent task as completed.
     *
     * @throws taskNotFoundException if no such task could be found in the list.
     */
    public void completeTask(ReadOnlyTask completedTask) throws TaskNotFoundException {
        assert completedTask != null;
        int internalIndex = findInternalIndex(completedTask);
        if (internalIndex > -1) {
            Task taskToUpdate = internalList.get(internalIndex);
            taskToUpdate.markComplete(completedTask);
            internalList.set(internalIndex, taskToUpdate);

        } else {
            throw new TaskNotFoundException();
        }
    }

    private int findInternalIndex(ReadOnlyTask completedTask) {
        for (int i = 0; i < internalList.size(); i++) {
            if (completedTask.getName() == internalList.get(i).getName()) {
                return i;
            }
        }
        return -1;
    }
  //@@author
    /**
     * Removes the equivalent task from the list.
     *
     * @throws taskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

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
