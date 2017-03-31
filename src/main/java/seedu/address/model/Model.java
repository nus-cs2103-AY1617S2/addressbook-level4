package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;
import seedu.address.model.person.UniqueEventList.DuplicateTimeClashException;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatsLeft newData);

    /** Returns the WhatsLeft */
    ReadOnlyWhatsLeft getWhatsLeft();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Deletes the given Event. */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /** Adds the given Task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Mark the given Task as complete **/
    void markTaskAsComplete(ReadOnlyTask taskToComplete) throws UniqueTaskList.TaskNotFoundException;

    /** Mark the given Task as pending **/
    void markTaskAsPending(ReadOnlyTask taskToRedo) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given Event
     * @throws DuplicateTimeClashException */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException, DuplicateTimeClashException;

    //@@author A0148038A
    /**
     * Updates the Event located at {@code filteredEventListIndex} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the Event's details causes the Event to be equivalent to
     *      another existing Event in the list.
     * @throws DuplicateTimeClashException if the updating Event clashes in time with another Event.
     * @throws IndexOutOfBoundsException if {@code filteredEventListIndex} < 0 or >= the size of the filtered list.
     */
    void updateEvent(Event eventToEdit, Event editedEvent)
            throws UniqueEventList.DuplicateEventException, DuplicateTimeClashException;

    /**
     * Updates the Task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the Task's details causes the Task to be equivalent to
     *      another existing Task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(Task taskToEdit, Task editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered Task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered Event list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filters of the filtered Task list and filtered Event list to show all activities */
    public void updateFilteredListToShowAll();

    /** Updates the filter of the filtered Task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered Event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);

    /** Empties the previousCommand list and adds the newest one in, always keeping only 1*/
    void storePreviousCommand(String command);
    /** Updates the filter of the filtered Event list to show completed task*/
    //void updateFilteredListToShowComplete();
    /** Updates the filter of the filtered Event list to show unfinished task*/
    //void updateFilteredListToShowIncomplete();

    //@@author A0121668A
    /** Sets the display status in model */
    void setDisplayStatus(String status);

    /** returns the current display status in model */
    String getDisplayStatus();

    //@@author
    /** Finds the index of the event in the filtered list*/
    int findEventIndex(Event event);

    /** Finds the index of the task in the filtered list*/
    int findTaskIndex(Task task);
}
