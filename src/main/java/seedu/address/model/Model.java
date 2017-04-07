package seedu.address.model;

import java.util.Date;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.storage.ImportEvent;
import seedu.address.commons.events.storage.ReadFromNewFileEvent;
import seedu.address.model.exceptions.NoPreviousCommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    static final String MESSAGE_ON_LIST = "List all tasks";
    static final String MESSAGE_ON_LISTCOMPLETED = "List completed tasks";
    static final String MESSAGE_ON_DELETE = "Task deleted";
    static final String MESSAGE_ON_ADD = "Task added";
    static final String MESSAGE_ON_RESET = "Task list loaded";
    static final String MESSAGE_ON_UPDATE = "Task updated";
    static final String MESSAGE_ON_SAVETO = "Save location changed to ";
    static final String MESSAGE_ON_EXPORT = "Data to be exported to ";
    static final String MESSAGE_ON_USETHIS = "Reading data from ";
    static final String MESSAGE_ON_IMPORT = "Importing data from ";

    /**
     * Show completed task list
     */
    void showCompletedTaskList();

    /**
     * Hide completed task list
     */
    void hideCompletedTaskList();

    /**
     * Clears existing backing model and replaces with the provided new data.
     *
     * @param clearPrevTasks
     *            TODO
     */
    void setData(ReadOnlyTaskManager newData, Boolean clearPrevTasks);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with
     * {@code editedTask}.
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredTaskListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateTask(int filteredTaskListIndex, Task editedTask) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to show all tasks */
    void indicateTaskManagerChanged(String message);

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords, Date date, Set<String> tagKeys);

    /** Informs eventbus about the change in save location */
    void updateSaveLocation(String path);

    /** Informs eventbus about the need to export data to specified path **/
    void exportToLocation(String path);

    /** Informs eventbus to read from new save location */
    void useNewSaveLocation(String path);

    /**
     * Divides task lists by categories into three separate ObservableList which
     * will be provided by UI
     *
     * @param taskListToday
     *            task to be displayed under category 'Today'
     * @param taskListFuture
     *            task to be displayed under category 'Future'
     * @param taskListCompleted
     *            task to be displayed under category 'Completed'
     */
    void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted);

    /**
     * Undo the last command entered by the user.
     *
     * @return the last command entered by the user
     * @throws NoPreviousCommandException
     *             if there are no previously executed commands to be reversed
     */
    public String undoLastCommand() throws NoPreviousCommandException;

    /**
     * Saves the command and current filteredTasks list.
     */
    public void saveCurrentState(String commandText);

    /**
     * Discard the last saved command and current filteredTasks list.
     */

    public void discardCurrentState();

    /*
     * translates task index on ui to internal integer index
     */
    int parseUIIndex(String uiIndex);

    /*
     * checks if a given ui index is present in model
     */
    boolean isValidUIIndex(String uiIndex);

    /**
     * Get the undone command entered by the user.
     *
     * @return the last undone command
     * @throws NoPreviousCommandException
     *             if there are no previously undone commands
     */
    String getRedoCommand() throws NoPreviousCommandException;

    /**
     * Clears the commands that were undone by the user
     */
    void clearRedoCommandHistory();

    /**
     * Resets data to the new set of read data
     */
    void handleReadFromNewFileEvent(ReadFromNewFileEvent event);

    /**
     * Prepare to import data and adds to existing set of tasks
     */
    void importFromLocation(String path);

    /**
     * Imports data and adds to existing set of tasks
     */
    void handleImportEvent(ImportEvent event);

    /*
     * Gets UI index by absolute index
     */
    public String getUIIndex(int index);
}
