package seedu.task.model;

import java.util.Set;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

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

    //@@author A0164889E
    /** Returns the filtered task complete list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListComplete();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords for FindCommand*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0164889E
    /** Updates the filter of the filtered task list to filter by the given keywords for GroupCommand*/
    void updateFilteredTaskListGroup(Set<String> keywords);

    //@@author A0164466X
    /** Updates the filter of the filtered task list to show all complete tasks */
    void updateFilteredListToShowComplete();

    /** Updates the filter of the filtered task list to show all incomplete tasks */
    void updateFilteredListToShowIncomplete();

    //@@author A0163848R
    /** Undoes the last modification made to the TaskManager. Returns if there is anything to undo. */
    boolean undoLastModification();

    /** Redoes the last modification made to the TaskManager. Returns if there is anything to redo. */
    boolean redoLastModification();

    /** Adds the current TaskManager state to the undo/redo history */
    void addToHistory(ReadOnlyTaskManager state);

    /** Adds entries from the given YTomorrow to the current YTomorrow and updates equivalent entries. */
    void mergeYTomorrow(ReadOnlyTaskManager add);
}
