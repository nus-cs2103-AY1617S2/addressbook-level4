package seedu.tache.model;

import java.util.Set;

import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueDetailedTaskList;
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

    /** Deletes the given task. */
    void deleteDetailedTask(ReadOnlyDetailedTask target) throws UniqueDetailedTaskList.DetailedTaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Adds the given detailed task */
    void addDetailedTask(DetailedTask detailedTask) throws UniqueDetailedTaskList.DuplicateDetailedTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the detailed task located at {@code filteredTaskListIndex} with {@code editedDetaileddTask}.
     *
     * @throws DuplicateDetailedTaskException if updating the detailed task's details causes the detailed
     *         task to be equivalent to another existing detailed task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredDetailedTaskListIndex} < 0 or >= the size of the
     *         filtered list.
     */
    void updateDetailedTask(int filteredDetailedTaskListIndex, ReadOnlyDetailedTask editedDetailedTask)
            throws UniqueDetailedTaskList.DuplicateDetailedTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered detailed task list as an {@code UnmodifiableObservableList<ReadOnlyDetailedTask>} */
    UnmodifiableObservableList<ReadOnlyDetailedTask> getFilteredDetailedTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredDetailedTaskList(Set<String> keywords);

}
