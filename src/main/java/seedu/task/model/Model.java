package seedu.task.model;

import java.util.Set;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    void updateTaskById(TaskId id, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0163744B
    /** Updates the filter of the filtered task list to filter by given completion */
    void updateFilteredListToShowCompletion(boolean isComplete);

    /** Updates the sorting of the filtered task list to sort by id */
    void updateFilteredListToSortById();

    /** Updates the sorting of the filtered task list to sort by due */
    void updateFilteredListToSortByDue();

    /** Updates the sorting of the filtered task list to sort by start */
    void updateFilteredListToSortByStart();

    /** Updates the sorting of the filtered task list to sort by end */
    void updateFilteredListToSortByEnd();
    //@@author
}
