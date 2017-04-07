package seedu.ezdo.model;

import java.util.ArrayList;
import java.util.EmptyStackException;

import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.exceptions.DateException;
import seedu.ezdo.commons.util.SearchParameters;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;
import seedu.ezdo.model.todo.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEzDo newData);

    /** Returns the EzDo */
    ReadOnlyEzDo getEzDo();

    /** Returns the UserPrefs */
    UserPrefs getUserPrefs();

    void sortTasks(SortCriteria sortCriteria, Boolean isSortedAscending);
  //@@author A0139248X
    /** Deletes the given tasks.
     * @throws TaskNotFoundException if any task is not found in ezDo
     */
    void killTasks(ArrayList<ReadOnlyTask> tasksToKill) throws UniqueTaskList.TaskNotFoundException;

    /** Adds a task.
     * @throws DuplicateTaskException if the same task (all attributes and fields same) is already in ezDo
     * @throws DateException if the start date is after the due date
     */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, DateException;

    /** Checks the task and makes sure the dates are logical.
     * @throws DateException if the start date is after the due date.
     */
    void checkTaskDate(ReadOnlyTask task) throws DateException;

    /** Toggles the tasks as done/undone. */
    boolean toggleTasksDone(ArrayList<Task> tasksToToggle);

    /** Toggles the tasks as done/undone. */
    boolean toggleTasksSelect(ArrayList<Task> tasksToToggle);

    /** Undo the previous undoable (add/edit/clear/kill/done) command
     * @throws EmptyStackException if there are no commands to undo*/
    void undo() throws EmptyStackException;

    /** Redo the previous undone command
     * @throws EmptyStackException if there were no undone commands to redo*/
    void redo() throws EmptyStackException;

    /** Update stacks when new command is executed*/
    void updateStacks();
  //@@author A0139248X

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, DateException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all undone tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by multiple fields*/
    void updateFilteredTaskList(SearchParameters searchParameters);

    /** Updates the filter of the filtered task list to show done tasks*/
    void updateFilteredDoneList();

}
