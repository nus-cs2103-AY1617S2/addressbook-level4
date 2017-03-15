package seedu.doist.model;

import java.util.Set;

import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.ListCommand.TaskType;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;
import seedu.doist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTodoList newData);

    /** Returns the TodoList */
    ReadOnlyTodoList getTodoList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Finishes the given task. */
    void finishTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException,
        UniqueTaskList.TaskAlreadyFinishedException;

    /** Unfinishes the given task. */
    void unfinishTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException,
        UniqueTaskList.TaskAlreadyUnfinishedException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    public void updateFilteredTaskList(TaskType type);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(UniqueTagList tags);

    void sortTasksByPriority();
}
