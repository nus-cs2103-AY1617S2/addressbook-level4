package project.taskcrusher.model;

import java.util.Set;

import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;
import project.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyUserInbox newData);

    /**
     * Undoes the previously performed action. If there is no undo to perform, returns false.
     */
    boolean undo();

    /**
     * Re-does the previously performed undo. If there is no undo to redo, returns false
     */
    boolean redo();

    /** Returns the UserInbox */
    ReadOnlyUserInbox getUserInbox();

    void prepareListsForUi();

    // ========== for tasks =================================================
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
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws UniqueTaskList.DuplicateTaskException;

    void markTask(int filteredListIndex, int markFlag);
    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredTaskListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    void updateFilteredTaskList(Timeslot userInterestedTimeSlot);

    void updateFilteredTaskListToShowComplete();

    public void updateFilteredTaskListToShowNone();

    // ========== for events =================================================

    /** deletes the given event */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /**
     * Updates the event located at {@code filteredEventListIndex} with
     * {@code editedEvent}.
     *
     * @throws UniqueEventList.DuplicateEventException
     *             if updating the event's details cause the event to be
     *             equivalent to another existing event in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code filteredEventListIndex} < 0 or >= the size of the
     *             filtered list.
     */
    void updateEvent(int fileteredEventListIndex, ReadOnlyEvent editedEvent)
            throws UniqueEventList.DuplicateEventException;

    /** Adds the given event */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

    /**
     * Returns the filtered event list as an
     * {@code UnmodifiableObservableList<ReadOnlyEvent>}
     */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filter of the filtered event list to show all events */
    void updateFilteredEventListToShowAll();

    /**
     * Updates the filter of the filtered event list to filter by the given
     * keywords
     */
    void updateFilteredEventList(Set<String> keywords);

    /**
     * Updates the filter of the filtered event list to filter by the given
     * timeslot
     */
    void updateFilteredEventList(Timeslot userInterestedTimeSlot);

    void markEvent(int filteredListIndex, int markFlag);

    void updateFilteredEventListToShowComplete();

    public void updateFilteredEventListToShowNone();

    /**
     * TODO this javadoc
     * @param filteredEventListIndex
     * @param timeslotIndex
     */
    void confirmEventTime(int filteredEventListIndex, int timeslotIndex);
}
