package seedu.tache.model;

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
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(ReadOnlyTask taskToEdit, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the all recurring ghost (not an actual task but will still be displayed to the user) tasks
     * as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    ObservableList<ReadOnlyTask> getAllRecurringGhostTasks();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0139925U
    /** Updates the filter of the filtered task list to show all uncompleted tasks */
    void updateFilteredListToShowUncompleted();

    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowCompleted();

    //@@author A0142255M
    /** Updates the filter of the filtered task list to show all uncompleted tasks */
    void updateFilteredListToShowTimed();

    //@@author A0139961U
    /** Updates the filter of the filtered task list to show all tasks due today */
    void updateFilteredListToShowDueToday();

    //@@author A0139961U
    /** Updates the filter of the filtered task list to show all tasks due today */
    void updateFilteredListToShowDueThisWeek();

    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowFloating();

    /** Returns the filtered task list type as a {@code String} */
    String getFilteredTaskListType();

    //@@author A0139925U
    /** Updates the filter of the current filtered task list to reflect changes */
    void updateCurrentFilteredList();
}
