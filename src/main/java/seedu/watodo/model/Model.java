package seedu.watodo.model;

import java.util.Set;

import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the Task Manager */
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

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredByNameTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords for tags*/
    void updateFilteredByTagsTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given number of days
     * @throws IllegalValueException */
    void updateFilteredByDatesTaskList(int days) throws IllegalValueException;

    /** Updates the filter of the filtered task list to filter by the given number of months
     * @throws IllegalValueException */
    void updateFilteredByMonthsTaskList(int months) throws IllegalValueException;

    /** Updates the filter of the filtered task list to filter by the given keyword for type*/
    void updateFilteredByTypesTaskList(String type);

    //@@author A0139845R
    /** Returns the last command saved in command history stack*/
    Command getPreviousCommand();

    /** Adds the executed command to the command history stack*/
    void addCommandToHistory(Command command);

    /** Returns the last undo saved in undo history stack*/
    Command getUndoneCommand();

    /** clears the undo history stack of all commands saved*/
    void clearRedo();

    //@@author

}
