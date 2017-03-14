package seedu.tasklist.model;

import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.EmptyModelStackException;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given person. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given person */
    void addTask(Task person) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the person located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Get the previous state (undo) of the task list */
    public void setPreviousState() throws EmptyModelStackException;

    /** Get the next state (redo) of the task list */
    public void setNextState() throws EmptyModelStackException;

    /** Enables undo to work after a clear command, by pushing the existing state into UndoStack. */
    public void enableUndoForClear();
}
