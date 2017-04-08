package seedu.task.model;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskComparable;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

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

    // @@author A0146757R
    /** Complete the given task. */
    void completeTask(int index) throws UniqueTaskList.TaskNotFoundException;

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

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    // @@author A0163845X
    void undo() throws Exception;

    // @@author A0163845X
    void updateBackup() throws DuplicateTaskException;

    // @@author A0163845X
    void sort(TaskComparable t);
    // @@author A0163845X

    void setTaskManager(Optional<ReadOnlyTaskManager> readTaskManager);
    // @@author A0163845X

    void redo() throws Exception;
    // @@author A0163845X

    public void filterStatus(String status);

    // @@author A0163845X
    void showcase(int numberOfTasks) throws DuplicateTaskException;

    // @@author A0163845X
    public void filter(Predicate<Object> pred);

}
