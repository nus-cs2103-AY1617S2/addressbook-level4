package seedu.address.model;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task);

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListByKeywords(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given date*/
    void updateFilteredTaskListByDate(Deadline deadline);

    /** Updates undo copy of task list*/
    void updateCopy(ReadOnlyTaskManager taskManager);

    /** Returns the Name of a Task as String at the input index of the task list*/
    String getCName(int index);

    /** Returns the Unix time of a Task as Long at the input index of the task list*/
    long getCTime(int index);

    /** Exchanges the location of two tasks indicated by input index*/
    void exchange(int i, int j);

    /** Returns the size of tasks list*/
    int getFilteredTasksSize();

    /** Deletes all tasks with Done status*/
    ArrayList<ReadOnlyTask> clearDone();

    /** Deletes the given task without writing to file. */
    void deleteBulkTask(ReadOnlyTask target) throws TaskList.TaskNotFoundException;

    /** Writes changes to data to file*/
    void indicateTaskManagerChanged();

    /** Returns undo copy of task list*/
    ReadOnlyTaskManager getCopy();

    /** Updates flag for copy of address book*/
    void updateFlag(String newFlag);

    /** Returns flag for copy of address book*/
    String getFlag();

}
