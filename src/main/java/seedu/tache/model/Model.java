package seedu.tache.model;

import java.util.Set;

import seedu.tache.commons.core.UnmodifiableObservableList;
<<<<<<< HEAD
import seedu.tache.model.person.Task;
import seedu.tache.model.person.ReadOnlyTask;
import seedu.tache.model.person.UniqueTaskList;
import seedu.tache.model.person.UniqueTaskList.DuplicateTaskException;
=======
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;
>>>>>>> ImplementTaskModels

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

<<<<<<< HEAD
    /** Deletes the given person. */

    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given person */
    void addPerson(Task person) throws UniqueTaskList.DuplicateTaskException;
=======
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
>>>>>>> ImplementTaskModels

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
<<<<<<< HEAD
     * @throws DuplicateTaskException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredPersonListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
=======
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
>>>>>>> ImplementTaskModels
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

<<<<<<< HEAD
    /** Updates the filter of the filtered person list to filter by the given keywords*/
=======
    /** Updates the filter of the filtered task list to filter by the given keywords*/
>>>>>>> ImplementTaskModels
    void updateFilteredTaskList(Set<String> keywords);

}
