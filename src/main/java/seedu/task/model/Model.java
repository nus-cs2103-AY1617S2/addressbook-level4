package seedu.task.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    //@@author A0139161J
    /** Adds an item into a specific index in the list of tasks*/
    void insertTasktoIndex(int index, Task task)
             throws UniqueTaskList.DuplicateTaskException;

    /** Replaces the current list of tasks with the specified list*/
    void loadList (ObservableList<ReadOnlyTask> list) throws DuplicateTaskException;
    //@@author

    /**
     * Updates the task located at {@code filteredTakkListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList <ReadOnlyTask> getFilteredTaskList();

    /** Returns the advanced filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList <ReadOnlyTask> getAdvancedFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0139161J
    /** Returns the filtered completed task list */
    ObservableList<ReadOnlyTask> getCompletedTaskList();

    /** Transfers the specified task to the completed task list section */
    void completeTask(Task t) throws DuplicateTaskException, TaskNotFoundException;

    void uncompleteTask(Task t) throws DuplicateTaskException, TaskNotFoundException;
    //@@author

    /** Updates the filter of the filtered task list to filter by the today's date */
    void updateUpcomingTaskList();

    /** Updates the filter of the filtered task list to filter by the given tags
     * @throws IllegalValueException */
    void updateFilteredTagTaskList(String tagName) throws IllegalValueException;

    /** Updates the filter of the filtered task list to filter by the given keywords, near match cases are allowed */
    void updateAdvancedFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the highest priority */
    void updatePriorityTaskList();
}
