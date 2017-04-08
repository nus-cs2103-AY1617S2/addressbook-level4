package seedu.taskit.model;

import java.util.Set;

import seedu.taskit.commons.core.UnmodifiableObservableList;
import seedu.taskit.commons.exceptions.NoValidStateException;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;
import seedu.taskit.model.task.UniqueTaskList.DuplicateMarkingException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getAddressBook();

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

    /** mark the task status to done or undone
     * @throws UniqueTaskList.DuplicateMarkingException
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
    */
    void markTask(ReadOnlyTask taskToMark, String parameter) throws UniqueTaskList.DuplicateMarkingException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given parameter*/
    int updateFilteredTaskList(String parameter);


    //@@author A0141011J
    /** Reverts to the previous state*/
    void revert() throws NoValidStateException;

    /** Redo the last undone command*/
    void redo() throws NoValidStateException;

    /** Records the current state of the model */
    void save();

}
