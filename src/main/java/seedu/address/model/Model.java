package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Task;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyUserInbox newData);

    /** Returns the AddressBook */
    ReadOnlyUserInbox getUserInbox();

    
    //========== for tasks =================================================
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredPersonListIndex} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the person's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    //========== for events =================================================
    
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;
    
    void updateEvent(int fileteredEventListIndex, ReadOnlyEvent editedEvent) 
            throws UniqueEventList.DuplicateEventException;
    
    void addEvent(Event event) throws UniqueEventList.EventNotFoundException;
    

}
