package typetask.model;

import java.util.Calendar;
import java.util.Set;

import typetask.commons.core.UnmodifiableObservableList;
import typetask.model.task.Priority;
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

    //@@author A0144902L
    /** Marks a given task as completed*/
    void completeTask(int index, ReadOnlyTask taskToComplete) throws TaskList.TaskNotFoundException;
    //@@author
    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0144902L
    /** Updates the filter of the filtered task list to filter by the given isCompleted value*/
    void updateFilteredTaskList(boolean showComplete);

    /** Updates the filter of the filtered task list to filter by the given priority value*/
    void updateFilteredTaskList(Priority priority);
    //@@author

    /** Updates the filter of the filtered task list to filter by the given Calendar value*/
    void updateFilteredTaskList(Calendar today);

    //@@author A0139926R
    /** Retrieves the tasks with similar date */
    void updateFilteredTaskList(String date);

    /** Stores current TaskManager state */
    void storeTaskManager(String command);

    /** Restores most recently stored TaskManager state */
    int restoreTaskManager();

    /** Undo most recently restored TaskManager state */
    int revertTaskManager();

    /** Removes most recently stored TaskManager state upon fail in check */
    void rollBackTaskManager(boolean isStorageOperation);

    /** Retrieves the index for task */
    int getFilteredTaskListIndex(ReadOnlyTask targetTask);

}
