package typetask.model;

import java.util.Set;

import typetask.commons.core.UnmodifiableObservableList;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
import typetask.model.task.TaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task);

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Stores current TaskManager state */
    void storeTaskManager(String command);

    /** Restores most recently stored TaskManager state */
    int restoreTaskManager();

    /** Undo most recently restored TaskManager state */
    int revertTaskManager();

    /** Removes most recently stored TaskManager state upon fail in check */
    void rollBackTaskManager(boolean isStorageOperation);

}
