package seedu.task.model.task;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.DuplicateDataException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.model.task.TaskStatus;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

	private final ObservableList<Task> internalList = FXCollections.observableArrayList();

	/**
	 * Returns true if the list contains an equivalent person as the given
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

	// @@author A0163845X
	public void clear() {
		while (!internalList.isEmpty()) {
			internalList.remove(0);
		}
		// TODO Auto-generated method stub

	}

	// @@author A0163845X
	public void addAll(UniqueTaskList toAdd) {
		for (Task t : toAdd) {
			internalList.add(t);
		}
	}

	// @@author A0163845X
	public static UniqueTaskList copy(UniqueTaskList toCopy) {
		UniqueTaskList ret = new UniqueTaskList();
		ret.addAll(toCopy);
		return ret;
	}

	/**
	 * Updates the task in the list at position {@code index} with
	 * {@code editedTask}.
	 *
	 * @throws DuplicateTaskException
	 *             if updating the task's details causes the task to be
	 *             equivalent to another existing task in the list.
	 * @throws IndexOutOfBoundsException
	 *             if {@code index} < 0 or >= the size of the list.
	 */

	public void updateTask(int index, ReadOnlyTask editedTask) throws DuplicateTaskException {
		assert editedTask != null;

		Task taskToUpdate = internalList.get(index);
		if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
			throw new DuplicateTaskException();
		}

		taskToUpdate.resetData(editedTask);
		// TODO: The code below is just a workaround to notify observers of the
		// updated person.
		// The right way is to implement observable properties in the Person
		// class.
		// Then, PersonCard should then bind its text labels to those observable
		// properties.
		internalList.set(index, taskToUpdate);
	}

	/**
	 * Removes the equivalent task from the list.
	 *
	 * @throws TaskNotFoundException
	 *             if no such person could be found in the list.
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
	 * Complete the equivalent task from the list.
	 *
	 * @throws TaskNotFoundException
	 *             if no such task could be found in the list.
	 */
	// @@author A0163845X
	public void complete(int index) {
		Task temp = internalList.get(index);
		temp.setTaskStatus(new TaskStatus(TaskStatus.MESSAGE_DONE));
		internalList.remove(index);
		internalList.add(temp);
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
