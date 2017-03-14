package seedu.taskboss.model;

import java.util.Set;

import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBoss newData);

    /** Returns the TaskBoss */
    ReadOnlyTaskBoss getTaskBoss();

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

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords as task name*/
    void updateFilteredTaskListByName(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords as start date*/
    void updateFilteredTaskListByStartDateTime(String keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords as end date*/
    void updateFilteredTaskListByEndDateTime(String keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords as category*/
    void updateFilteredTaskListByCategory(Category category);

}
