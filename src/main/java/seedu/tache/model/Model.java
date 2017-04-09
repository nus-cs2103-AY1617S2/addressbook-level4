package seedu.tache.model;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Adds the given task to the specified index */
    void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates {@code taskToEdit} to {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(ReadOnlyTask taskToEdit, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates all tasks in {@code tasksToEdit} to their corresponding task in {@code editedTasks}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     * @return Array of tasks that have been successfully updated
     */
    List<ReadOnlyTask> updateMultipleTasks(ReadOnlyTask[] tasksToEdit, ReadOnlyTask[] editedTasks)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    //@@author A0150120H
    /** Returns the index of the specified task in the filtered task list
     *
     * @param targetTask Task to search for in the filtered task list
     * @return index of targetTask if found, -1 otherwise
     */
    int getFilteredTaskListIndex(ReadOnlyTask targetTask);
    //@@author

    //@@author A0139925U
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to show all uncompleted tasks */
    void updateFilteredListToShowUncompleted();

    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowCompleted();

    /** Returns the all completed recurring ghost (not an actual task but will still be displayed to the user) tasks
     * as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    ObservableList<ReadOnlyTask> getAllCompletedRecurringGhostTasks();

    /** Returns the all uncompleted recurring ghost (not an actual task but will still be displayed to the user) tasks
     * as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    ObservableList<ReadOnlyTask> getAllUncompletedRecurringGhostTasks();

    //@@author A0142255M
    /** Updates the filter of the filtered task list to show all timed tasks */
    void updateFilteredListToShowTimed();

    //@@author A0139961U
    /** Updates the filter of the filtered task list to show all tasks due today */
    void updateFilteredListToShowDueToday();

    //@@author A0139961U
    /** Updates the filter of the filtered task list to show all tasks due this week */
    void updateFilteredListToShowDueThisWeek();

    /** Updates the filter of the filtered task list to show all floating tasks */
    void updateFilteredListToShowFloating();

    /** Updates the filter of the filtered task list to show all overdue tasks */
    void updateFilteredListToShowOverdueTasks();

    /** Returns the filtered task list type as a {@code String} */
    String getFilteredTaskListType();
}
