package typetask.model;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
import typetask.model.task.TaskList;

/**
 * Wraps all data at the TaskManager level
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final TaskList tasks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new TaskList();
    }

    public TaskManager() {}

    /**
     * Creates a TaskManager using the Tasks in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        setTasks(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the TypeTask.
     *
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask) {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        tasks.updateTask(index, editedTask);
    }

    public boolean removeTask(ReadOnlyTask key) throws TaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskList.TaskNotFoundException();
        }
    }

    //@@author A0144902L
    /**
     * Marks a task in TypeTask as complete.
     */
    public boolean completeTask(ReadOnlyTask target) throws TaskList.TaskNotFoundException {
        Task editedTask = new Task(target);
        tasks.completeTask(editedTask);
        return false;
    }

    public ObservableList<ReadOnlyTask> getIncompleteList() {
        FilteredList<Task> incompleteList = new FilteredList<>(tasks.asObservableList().
                filtered(p -> !p.getIsCompleted()));
        return new UnmodifiableObservableList<>(incompleteList);
    }
  //@@author
//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, ";
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
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
