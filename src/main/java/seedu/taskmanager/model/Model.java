package seedu.taskmanager.model;

import java.util.Set;

import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Re-save data when save location has changed. */
    void saveTaskManager();

    /** Redo previous action of task manager. */
    public void redoTaskManager();

    /** Undo previous action of task manager. */
    void undoTaskManager();

    /** Deletes the task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    // @@author A0142418L
    /**
     * Deletes tasks by their date. Returns the number of tasks deleted.
     */
    int deleteTasksDate(UnmodifiableObservableList<ReadOnlyTask> targets) throws UniqueTaskList.TaskNotFoundException;

    /**
     * Deletes the task by its name. Returns the number of tasks deleted.
     */
    int deleteTasksName(UnmodifiableObservableList<ReadOnlyTask> targets, String toDeleteTaskName)
            throws UniqueTaskList.TaskNotFoundException;

    // @@author
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws UniqueTaskList.DuplicateTaskException;

    // @@author A0139520L
    void markTask(int filteredTaskListIndex) throws DuplicateTaskException;

    // @@author
    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    void updateFilteredTaskListForListCommand(Set<String> keywords, boolean isComplete);

    // @@author A0139520L
    /**
     * Updates the filter of the filtered task list to filter by completed tasks
     */
    void updateFilteredTaskListToShowByCompletion(boolean bool);

    // @@author A0142418L
    /**
     * Updates the filter of the filtered task list to filter by completed tasks
     */
    void updateFilteredTaskListForInitialView();

    void unmarkTask(int filteredTaskListIndex) throws DuplicateTaskException;

    int isBlockedOutTime(Task task) throws DuplicateTaskException;

    int isBlockedOutTime(Task t, int UpdateTaskIndex) throws DuplicateTaskException;

}
