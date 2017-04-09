package seedu.whatsleft.model;

import java.util.Set;

import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList;
import seedu.whatsleft.model.activity.UniqueEventList.DuplicateEventException;
import seedu.whatsleft.model.activity.UniqueTaskList;
import seedu.whatsleft.model.activity.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatsLeft newData);

    //@@author A0148038A
    /** Clears all events in WhatsLeft*/
    void resetEvent();

    /** Clears all tasks in WhatsLeft*/
    void resetTask();

    //@@author
    /** Returns the WhatsLeft */
    ReadOnlyWhatsLeft getWhatsLeft();

    /** Adds the given Event
     * @throws DuplicateTimeClashException */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

    /** Adds the given Task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Deletes the given Event. */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Mark the given Task as complete **/
    void markTaskAsComplete(ReadOnlyTask taskToComplete) throws UniqueTaskList.TaskNotFoundException;

    /** Mark the given Task as pending **/
    void markTaskAsPending(ReadOnlyTask taskToRedo) throws UniqueTaskList.TaskNotFoundException;

    //@@author A0148038A
    /**
     * Updates the Event located at {@code filteredEventListIndex} with {@code editedEvent}.
     * @param the event to be edited and the edited event
     * @throws DuplicateEventException if updating the Event's details causes the Event to be equivalent to
     *      another existing Event in the list.
     */
    void updateEvent(Event eventToEdit, Event editedEvent)
            throws UniqueEventList.DuplicateEventException;

    /**
     * Updates the Task located at {@code filteredTaskListIndex} with {@code editedTask}.
     * @param the task to be edited and the edited task
     * @throws DuplicateTaskException if updating the Task's details causes the Task to be equivalent to
     *      another existing Task in the list.
     */
    void updateTask(Task taskToEdit, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered Task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered Event list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filters of the filtered Event list and filtered Task list to show all activities */
    public void updateFilteredListToShowAll();

    /** Updates the filter of the filtered Task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered Event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);

    /** Empties the previousCommand list and adds the newest one in, always keeping only one command*/
    void storePreviousCommand(String command);

    //@@author A0121668A
    /** Sets the display status in model */
    void setDisplayStatus(String status);

    /** returns the current display status in model */
    String getDisplayStatus();

    //@@author A0148038A
    /** Finds the index of the event in the filtered event list*/
    int findEventIndex(Event event);

    /** Finds the index of the task in the filtered task list*/
    int findTaskIndex(Task task);

    //@@author A0110491U
    /** Finds whether there is a clash of timing of event */
    boolean eventHasClash(Event toAddEvent);
}
