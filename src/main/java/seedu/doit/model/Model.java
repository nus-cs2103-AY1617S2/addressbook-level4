package seedu.doit.model;

import java.util.Set;

import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyItemManager newData);

    /**
     * Returns the TaskManager
     */
    ReadOnlyItemManager getTaskManager();

    /**
     * Deletes the given task.
     */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /**
     * Adds the given task
     */
    void addTask(Task task) throws DuplicateTaskException;

    /**
     * Marks the given task as completed
     */
    void markTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException    if updating the task's details causes the task to be equivalent to
     *                                   another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
        throws DuplicateTaskException;

    /**
     * Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to show all tasks
     */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given keywords
     */
    void updateFilteredTaskList(Set<String> nameKeywords, Set<String> priorityKeywords,
            Set<String> descriptionKeywords);



}
