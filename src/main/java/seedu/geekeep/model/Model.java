
package seedu.geekeep.model;

import java.util.List;
import java.util.Set;

import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.DuplicateTaskException;

public interface Model {
    //@@author A0121658E
    /**
     * Signals that an undo command would fail because there is nothing to undo.
     */
    public static class NothingToUndoException extends Exception {
    }

    /**
     * Signals that an undo command would fail because there is nothing to redo.
     */
    public static class NothingToRedoException extends Exception {
    }

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Deletes the task */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the GeeKeep */
    ReadOnlyGeeKeep getGeeKeep();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyGeeKeep newData);

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by status of the tasks */
    void updateFilteredTaskListToShowDone();

    /** Updates the filter of the filtered task list to filter by status of the tasks */
    void updateFilteredTaskListToShowUndone();

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code updatedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be equivalent to another existing task in the
     *             list.
     * @throws IllegalValueException
     *             if the task's startDateTime is not matched with a later endDateTime
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask updatedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalValueException;

    /** Mark the specified task as done */
    void markTaskDone(int filteredTaskListIndex);

    /** Mark the specified task as undone */
    void markTaskUndone(int filteredTaskListIndex);

    String undo() throws NothingToUndoException;

    String redo() throws NothingToRedoException;

    List<String> getCommandHistory();

    void appendCommandHistory(String commandText);

    void updateUndoableCommandHistory(String commandText);

}

