package todolist.model;

import java.util.Set;

import todolist.commons.core.UnmodifiableObservableList;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;
import todolist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given Task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given Task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the Task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the Task's details causes the Task to be
     *             equivalent to another existing Task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws UniqueTaskList.DuplicateTaskException;

    // @@ A0143648Y
    /**
     * Returns the filtered Task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatList();

    UnmodifiableObservableList<ReadOnlyTask> getListFromChar(Character type);

    // @@
    /** Updates the filter of the filtered Task list to show all Tasks */
    void updateFilteredListToShowAll();

    String getTagListToString();

    /**
     * Updates the filter of the filtered Task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

}
