package seedu.watodo.model;

import java.util.Set;

import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.model.task.FloatingTask;
import seedu.watodo.model.task.ReadOnlyFloatingTask;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the Watodo */
    ReadOnlyTaskList getWatodo();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyFloatingTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(FloatingTask task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyFloatingTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords for tags*/
    void updateFilteredByTagsTaskList(Set<String> keywords);

}
