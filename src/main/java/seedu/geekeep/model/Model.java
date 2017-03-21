package seedu.geekeep.model;

import java.util.Set;

import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.DuplicateTaskException;

public interface Model {
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Deletes the task */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

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

    /** Updates the filter of the filtered task list to filter by status of the tasks */
    void updateFilteredTaskListToShowDone();

    /** Updates the filter of the filtered task list to filter by status of the tasks */
    void updateFilteredTaskListToShowUndone();

    void updateFilteredTaskListToShowEvents();

    void updateFilteredTaskListToShowDeadlines();

    void updateFilteredTaskListToShowFloatingTasks();

    /**
     * Updates the person located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be equivalent to another existing person in the
     *             list.
     * @throws IllegalValueException
     *             if the task's startDateTime is not matched with a later endDateTime
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalValueException;

    /** Mark the specified task as done */
    void markTaskDone(int filteredTaskListIndex);

    /** Mark the specified task as undone */
    void markTaskUndone(int filteredTaskListIndex);

}

