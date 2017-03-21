package seedu.task.model;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the task manager level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

	private final UniqueTaskList tasks;

	/*
	 * The 'unusual' code block below is an non-static initialization block,
	 * sometimes used to avoid duplication between constructors. See
	 * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
	 *
	 * Note that non-static init blocks are not recommended to use. There are
	 * other ways to avoid duplication among constructors.
	 */
	{
		tasks = new UniqueTaskList();
	}

	public TaskManager() {
	}

	/**
	 * Creates an TaskManager using the Tasks in the {@code toBeCopied}
	 */
	public TaskManager(ReadOnlyTaskManager toBeCopied) {
		this();
		resetData(toBeCopied);
	}

	//// list overwrite operations

	public void setTasks(List<? extends ReadOnlyTask> tasks) throws UniqueTaskList.DuplicateTaskException {
		this.tasks.setTasksJob(tasks);
	}

	public void resetData(ReadOnlyTaskManager newData) {
		assert newData != null;
		try {
			setTasks(newData.getTaskList());
		} catch (UniqueTaskList.DuplicateTaskException e) {
			assert false : "Task Manager should not have duplicate tasks";
		}
		// syncMasterTagListWith(tasks);
	}

	//// task-level operations

	/**
	 * Adds a task to the task manager.
	 * 
	 * @throws UniqueTaskList.DuplicateTaskException
	 *             if an equivalent person already exists.
	 */
	public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
		// syncMasterTagListWith(p);
		tasks.add(p);
	}

	/**
	 * Updates the task in the list at position {@code index} with
	 * {@code editedReadOnlyTask}.
	 * 
	 * @throws DuplicateTaskException
	 *             if updating the task's details causes the task to be
	 *             equivalent to another existing task in the list.
	 * @throws IndexOutOfBoundsException
	 *             if {@code index} < 0 or >= the size of the list.
	 */
	public void updateTask(int index, ReadOnlyTask editedReadOnlyTask) throws UniqueTaskList.DuplicateTaskException {
		assert editedReadOnlyTask != null;

		Task editedTask = new Task(editedReadOnlyTask);
		tasks.updateTask(index, editedTask);
	}

	public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
		if (tasks.remove(key)) {
			return true;
		} else {
			throw new UniqueTaskList.TaskNotFoundException();
		}
	}

	@Override
	public String toString() {
		return tasks.asObservableList().size() + " tasks";
		// TODO: refine later
	}

	@Override
	public ObservableList<ReadOnlyTask> getTaskList() {
		return new UnmodifiableObservableList<>(tasks.asObservableList());
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskManager // instanceof handles nulls
						&& this.tasks.equals(((TaskManager) other).tasks));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(tasks);
	}
}
