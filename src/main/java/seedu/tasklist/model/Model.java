package seedu.tasklist.model;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
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

    //@@author A0143355J
    /** Returns today's task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getTodayTaskList();

    //@@author A0143355J
    /** Returns tomorrow's task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getTomorrowTaskList();

    //@@author
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0139747N
    /** Get the previous state (undo) of the task list */
    public void setPreviousState() throws EmptyStackException;

    /** Get the next state (redo) of the task list */
    public void setNextState() throws EmptyStackException;

    /** Enables undo to work after a clear command, by pushing the existing state into UndoStack. */
    public void enableUndoForClear();
//@@author A0141993X
    /**Sort tasks according to parameter specified by user */
    public void sortTaskList(String parameter);
//@@author
    /** Updates the filter of the filered task list to filter by the given tag keywords*/
    void updateFilteredTaskListTag(Set<String> keyword);
//@@author A0141993X
    /** Loads file from file path
     * @throws IOException
     * */
    void loadTaskList(String filePath) throws IOException;

    /** Save file given a file path
     * @throws IOException
     * */
    void saveTaskList(String filePath) throws IOException;

    void updateFilteredTaskListStatus(Set<String> keywords);

    void updateTodaysTaskList();

    void updateTomorrowsTaskList();
}
