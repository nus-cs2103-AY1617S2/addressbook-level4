package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;
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
    void deleteTask(ReadOnlytask target) throws UniqueTaskList.TaskNotFoundException;

    /** Deletes the given Event. */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /** Adds the given Task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Adds the given Event */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

    /**
     * Updates the Task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the Task's details causes the Task to be equivalent to
     *      another existing Task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the Event located at {@code filteredEventListIndex} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the Event's details causes the Event to be equivalent to
     *      another existing Event in the list.
     * @throws IndexOutOfBoundsException if {@code filteredEventListIndex} < 0 or >= the size of the filtered list.
     */
    void updateEvent(int filteredEventListIndex, ReadOnlyEvent editedEvent)
            throws UniqueEventList.DuplicateEventException;

    /** Returns the filtered Task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered Event list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filter of the filtered Task list to show all activities */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered Event list to show all activities */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered Task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered Event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
}
