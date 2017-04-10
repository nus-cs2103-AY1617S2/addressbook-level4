package seedu.jobs.model;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Set;

import seedu.jobs.commons.core.UnmodifiableObservableList;
import seedu.jobs.commons.exceptions.DuplicateDataException;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data.
     * @throws IllegalTimeException */
    void resetData(ReadOnlyTaskBook newData) throws IllegalTimeException;

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Completes the given task
     * @throws TaskNotFoundException
     * @throws DuplicateDataException */
    void completeTask(int index, ReadOnlyTask task) throws TaskNotFoundException;

    /**
     * Updates the person located at {@code filteredPersonListIndex} with {@code editedPerson}.
     * @param editedTask
     *
     * @throws DuplicateTaskException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws IllegalTimeException
     * @throws IndexOutOfBoundsException if {@code filteredPersonListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredPersonListIndex, ReadOnlyTask taskToEdit, Task editedTask)
         throws UniqueTaskList.DuplicateTaskException, IllegalTimeException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered tasks list to show all tasks */
    void updateFilteredListToShowAll();

  //@@author A0164440M
    /** Updates the filter of the filtered tasks list to show all tasks based on arguments */
    void updateFilteredListToShowAll(Set<String> keywords);
  //@@author

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);


    /**
     * Undo the last command which has modified tasks or tags
     */

    void undoCommand() throws EmptyStackException;

    void redoCommand() throws EmptyStackException;

    void changePath(String path) throws IOException;

    void set(String email, String password);

}
