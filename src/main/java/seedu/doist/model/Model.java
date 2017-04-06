package seedu.doist.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.ListCommand.TaskType;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskCombinedComparator;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.TaskDate;
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
    int finishTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException,
        UniqueTaskList.TaskAlreadyFinishedException;

    /** Unfinishes the given task. */
    int unfinishTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException,
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
    int updateTask(int filteredTaskListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show the default listing of tasks */
    void updateFilteredListToShowDefault();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(String keywords);

    /** Updates the filter of the filtered task list to filter by the given task type, tags and dates*/
    void updateFilteredTaskList(TaskType type, UniqueTagList tags, TaskDate dates);

    /** Sorts the task accorfing to the specific comparator passed as an argument */
    void sortTasks(Comparator<ReadOnlyTask> comparator);

    /** Sorts the tasks according to the comparators defined in the list*/
    void sortTasks(List<SortType> sortTypes);

    /** Sorts the tasks according to the default sorting */
    void sortTasksByDefault();

    /** Returns default sorting */
    List<SortType> getDefaultSorting();

    /** Parses a list of sort types to a combined comparator for ReadOnlyTask */
    ReadOnlyTaskCombinedComparator parseSortTypesToComparator(List<SortType> sortTypes);

    /** Returns a list of task descriptions */
    ArrayList<String> getAllNames();

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

    /** Remove the alias if it exists, otherwise nothing happens */
    void removeAlias(String alias);

}
