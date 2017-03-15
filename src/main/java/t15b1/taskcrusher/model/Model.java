package t15b1.taskcrusher.model;

import java.util.Date;
import java.util.Set;

import t15b1.taskcrusher.commons.core.UnmodifiableObservableList;
import t15b1.taskcrusher.model.event.Event;
import t15b1.taskcrusher.model.event.ReadOnlyEvent;
import t15b1.taskcrusher.model.event.UniqueEventList;
import t15b1.taskcrusher.model.task.ReadOnlyTask;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;
import t15b1.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;

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
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
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

    /** Updates the filter of the filtered task list to filter by the given up-to Date*/
    void updateFilteredTaskList(Date upToDate);

    //========== for events =================================================

    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    void updateEvent(int fileteredEventListIndex, ReadOnlyEvent editedEvent)
            throws UniqueEventList.DuplicateEventException;

    void addEvent(Event event) throws UniqueEventList.EventNotFoundException;


}
