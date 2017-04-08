package seedu.taskmanager.model;

import java.util.Date;
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

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    // @@author A0131278H
    /**
     * Sorts task list based on keywords (startdate or enddate).
     */
    void sortTasks(String keyword);
    
    /**
     * highlights changed task in task list.
     */
    void highlightTask(ReadOnlyTask task);

    /** Returns the current tab selected. */
    String getSelectedTab();
    // @@author

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

    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    // @@author A0131278H

    /**
     * Returns the selected task list according to tab selection as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getSelectedTaskList();

    /**
     * Returns the filtered task list of incomplete tasks as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredToDoTaskList();

    /**
     * Returns the filtered task list of completed tasks as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList();
    // @@author

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    // @@author A0140032E
    /**
     * Updates the filter of the filtered task list to filter by the given
     * date(s)
     */
    void updateFilteredTaskList(Date date);

    void updateFilteredTaskList(Date startDateCriteria, Date endDateCriteria);
    // @@author

    // @@author A0131278H
    /** Sets the currently selected tab in model */
    void setSelectedTab(String selectedTab);
    // @@author
}
