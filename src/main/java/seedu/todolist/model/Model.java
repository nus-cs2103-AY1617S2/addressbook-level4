package seedu.todolist.model;

import java.util.Set;

import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given task. */
    void deleteTask(Task target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Mark the given task as completed */
    void completeTask(int filteredTaskListIndex, Task targetTask)
            throws UniqueTaskList.TaskNotFoundException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<Task> getFilteredTaskList();

    /** Returns the filtered task list of incomplete tasks as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    //@@author A0139633B
    UnmodifiableObservableList<Task> getFilteredIncompleteTaskList();

    /** Returns the filtered task list of complete tasks as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    //@@author A0139633B
    UnmodifiableObservableList<Task> getFilteredCompleteTaskList();

    /** Returns the filtered task list of overdue tasks as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    //@@author A0139633B
    UnmodifiableObservableList<Task> getFilteredOverdueTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
