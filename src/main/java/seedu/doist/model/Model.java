package seedu.doist.model;

import java.nio.file.Path;
import java.util.List;
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
    int addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

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

    /** Updates the filter of the filtered task list to filter by the given task type and tags*/
    void updateFilteredTaskList(TaskType type, UniqueTagList tags);

    /** Sorts the tasks by Priority */
    void sortTasksByPriority();

    //========== handle undo and redo operation =================================================
    void saveCurrentToHistory();
    void recoverPreviousTodoList();
    void recoverNextTodoList();

    ///// Alias List Map
    /** Returns the AliasListMap */
    ReadOnlyAliasListMap getAliasListMap();

    /** Sets an alias in the AliasListMap */
    void setAlias(String alias, String commandWord);

    /** Get the alias list of a defaultCommandWord */
    List<String> getAliasList(String defaultCommandWord);

    /** Get the valid command list of a defaultCommandWord */
    List<String> getValidCommandList(String defaultCommandWord);

    /** Get the set of default command words */
    Set<String> getDefaultCommandWordSet();

    /** Resets alias list to default */
    void resetToDefaultCommandWords();

    /** Change absolute path in config */
    void changeConfigAbsolutePath(Path path);
}
