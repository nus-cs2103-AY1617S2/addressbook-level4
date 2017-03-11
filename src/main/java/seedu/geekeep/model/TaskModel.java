package seedu.geekeep.model;

import java.util.Set;

import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.DuplicateTaskException;

public interface TaskModel {
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Deletes the task */
    void deteleTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the task manager */
    ReadOnlyTaskManager getTaskManager();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    /**
     * Updates the person located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be equivalent to another existing person in the
     *             list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

}
