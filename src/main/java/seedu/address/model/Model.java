package seedu.address.model;

import java.io.IOException;
import java.util.Date;
//import java.util.List;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.validate.ValidationException;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model.StateLimitReachedException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Raises an event to indicate the storage file path has changed */
    void indicateAddressBookFilePathChanged(String filePath) throws DataConversionException, IOException;

    /** Raises an event to indicate the user prefs file path has changed */
    void indicateUserPrefsFilePathChanged(String filePath);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the index of given task in its displayed task list. */
    int getDisplayedIndex(ReadOnlyTask task);

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

    /**
     * Saves the current task list to and ICS formatted file at the specified path.
     * @param filePath The path to file output.
     * @throws ValidationException Thrown when the task list data is not compatible with ICS format.
     * @throws IOException Thrown when the specified file path is invalid.
     */
    void saveTasksToIcsFile(String filePath) throws ValidationException, IOException;

    /**
     * Extracts events from an ICS file and adds them to the current task list.
     * @param filePath The path to file.
     * @throws IllegalValueException Thrown when the imported data is not compatible with task format.
     * @throws IOException Thrown when the specified file path is invalid.
     * @throws ParserException Thrown when data in the ICS file is not compliant to ICS file format.
     * @throws UniqueTaskList.DuplicateTaskException Thrown when a task to be imported already exists in
     * the task manager.
     */
    void addTasksFromIcsFile(String filePath)
            throws IOException, ParserException, IllegalValueException, UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered non-floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getNonFloatingTaskList();

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFloatingTaskList();

    /** Returns the filtered completed task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCompletedTaskList();

    /** Updates the filter of the filtered task lists to show all tasks */
    void updateFilteredTaskListToShowAllTasks();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListToShowFilteredTasks(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given tag list*/
    void updateFilteredTaskListToShowFilteredTasks(UniqueTagList tagList);

    /** Updates the filter of the filtered task list to filter by the given date*/
    void updateFilteredTaskListToShowFilteredTasks(Date date);

    /**
     * Overwrites AddressBook state to 1 step forwards.
     */
    void setAddressBookStateForwards() throws StateLimitReachedException;

    /**
     * Overwrites AddressBook state to 1 step backwards.
     */
    void setAddressBookStateBackwards() throws StateLimitReachedException;

    /**
     * Overwrites TaskManager state to zero.
     */
    void setAddressBookStateToZero() throws StateLimitReachedException;

    /**
     * Overwrites TaskManager state to the furthermost.
     */
    void setAddressBookStateToFrontier() throws StateLimitReachedException;

    void setCurrentComparator(String type);
    /**
     * Signals that the state change command would fail because
     * the border of the state space is reached.
     */
    public static class StateLimitReachedException extends Exception {}

}
