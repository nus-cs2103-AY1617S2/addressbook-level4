package seedu.doit.model;

import java.util.Set;

import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.model.item.Event;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueEventList.DuplicateEventException;
import seedu.doit.model.item.UniqueEventList.EventNotFoundException;
import seedu.doit.model.item.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.doit.model.item.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyTaskManager newData);

    /**
     * Returns the TaskManager
     */
    ReadOnlyTaskManager getTaskManager();

    /**
     * Deletes the given task.
     */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /**
     * Deletes the given floatingTask.
     */
    void deleteFloatingTask(ReadOnlyFloatingTask target) throws FloatingTaskNotFoundException;

    /**
     * Deletes the given event.
     */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * Adds the given task
     */
    void addTask(Task task) throws DuplicateTaskException;

    /**
     * Adds the given floatingTask
     */
    void addFloatingTask(FloatingTask floatingTask) throws DuplicateFloatingTaskException;

    /**
     * Adds the given event
     */
    void addEvent(Event event) throws DuplicateEventException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException    if updating the task's details causes the task to be equivalent to
     *                                   another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
        throws DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredFloatingTaskListIndex} with {@code editedFloatingTask}.
     *
     * @throws DuplicateFloatingTaskException if updating the floatingTask's details causes the task
     *         to be equivalent to another existing floatingTask in the list.
     * @throws IndexOutOfBoundsException if {@code filteredFloatingTaskListIndex} < 0
     *         or >= the size of the filtered list.
     */
    void updateFloatingTask(int filteredFloatingTaskListIndex, ReadOnlyFloatingTask editedFloatingTask)
        throws DuplicateFloatingTaskException;

    /**
     * Updates the task located at {@code filteredEventListIndex} with {@code editedEvent}.
     *
     * @throws DuplicateEventException    if updating the event's details causes the task to be equivalent to
     *                                   another existing event in the list.
     * @throws IndexOutOfBoundsException if {@code filteredEventListIndex} < 0 or >= the size of the filtered list.
     */
    void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
        throws DuplicateEventException;

    /**
     * Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyFloatingTask>}
     */
    UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();

    /**
     * Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyEvent>}
     */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Updates the filter of the filtered task list to show all tasks
     */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

}
