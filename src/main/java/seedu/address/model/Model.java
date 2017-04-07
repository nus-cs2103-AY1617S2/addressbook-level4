package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

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
    //@@author A0148052L
    /** Check if commandStack is empty*/
    boolean isCommandStackEmpty();

    /** Check if undoneCommand stack is empty*/
    boolean isUndoneCommandEmpty();

    /** Check if undoneStatus stack is empty*/
    boolean isUndoneStatusEmpty();

    /** Pushes the command to commandStack*/
    void pushCommand(String command);

    /** Pushes the currentStatus to statusStack*/
    void pushStatus(ReadOnlyTaskList currentStatus);

    /** Pops the latest undone status from undoneStatus stack and push it to statusStack*/
    void popUndoneStatus();

    /** Pops the latest undone command from undoneCommand stack and push it to commandStack*/
    void popUndoneCommand();

    /** Pops the current status from statusStack*/
    void popCurrentStatus();

    /** Returns the latest command*/
    String getPreviousCommand();

    /** Returns the latest status*/
    TaskList getPrevStatus();
    //@@author

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to show all done tasks */
    void updateFilteredListToShowDone();

    /** Updates the filter of the filtered task list to show all floating tasks */
    void updateFilteredListToShowFloating();

    /** Updates the filter of the filtered task list to show all overdue tasks */
    void updateFilteredListToShowOverdue();

    /** Updates the filter of the filtered task list to show all pending tasks */
    void updateFilteredListToShowPending();

    /** Updates the filter of the filtered task list to show all today tasks */
    void updateFilteredListToShowToday();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

}
