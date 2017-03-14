package seedu.address.model;

//import java.util.List;
import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.Model.StateLimitReachedException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     * @param targetList
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(String targetList, int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered non-floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getNonFloatingTaskList();

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFloatingTaskList();

    /** Returns the filtered completed task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCompletedTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAllNonFloating();
    
    /** Updates the filter of the filtered task list to show all floating tasks */
    void updateFilteredListToShowAllFloatingTasks();

    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowAllCompletedTasks();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowFilteredNonFloatingTasks(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show all floating tasks */
    void updateFilteredListToShowFilteredFloatingTasks(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowFilteredCompletedTasks(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /**
     * Overwrites AddressBook state to 1 step forwards.
     */
    void setAddressBookStateForwards() throws StateLimitReachedException;

    /**
     * Overwrites AddressBook state to 1 step backwards.
     */
    void setAddressBookStateBackwards() throws StateLimitReachedException;

    /**
     * Signals that the state change command would fail because
     * the border of the state space is reached.
     */
    public static class StateLimitReachedException extends Exception {}

}
