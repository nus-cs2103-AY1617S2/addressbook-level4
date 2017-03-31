package seedu.onetwodo.model;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList;
import seedu.onetwodo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

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

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /**
     * Mark the given tasks completed.
     *
     * @throws IllegalValueException
     */
    void doneTask(ReadOnlyTask taskToComplete) throws IllegalValueException;

    /**
     * Mark the given tasks for today.
     *
     * @throws IllegalValueException
     */
    void todayTask(ReadOnlyTask taskForToday) throws IllegalValueException;

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
    void updateTask(ReadOnlyTask taskToEdit, int internalIdx, Task editedTask)
            throws TaskNotFoundException, UniqueTaskList.DuplicateTaskException;

    void addTaskForEdit(int internalIdx, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateByNameDescriptionTag(Set<String> keywords);

    /**
     * Updates the filter of the filtered task list to display all undone tasks
     */
    void updateFilteredUndoneTaskList();

    /**
     * Updates the filter of the filtered task list to display all done tasks
     */
    void updateFilteredDoneTaskList();

    /**
     * Updates the filter of the filtered task list to display all tasks for
     * today
     */
    void updateFilteredTodayTaskList();

    /** Return the done status of particular task */
    DoneStatus getDoneStatus();

    /** Set the done status of particular task */
    void setDoneStatus(DoneStatus doneStatus);

    void updateByDoneStatus();

    void resetSearchStrings();

    void updateBySearchStrings();

    void updateByTaskType(TaskType taskType);

    FilteredList<ReadOnlyTask> getFilteredByDoneFindType(TaskType taskType);

    int getTaskIndex(ReadOnlyTask task);

    String undo() throws EmptyHistoryException;

    String redo() throws EmptyHistoryException;

    void clear();

    void updateByDoneDatePriorityTags(EndDate before, StartDate after, Priority priority, Set<Tag> tags);

    void sortBy(SortOrder sortOrder);

    public void jumpToNewTask(ReadOnlyTask task);

}
