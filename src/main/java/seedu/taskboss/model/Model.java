package seedu.taskboss.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;

import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBoss newData) throws IllegalValueException;

    /** Returns the TaskBoss */
    ReadOnlyTaskBoss getTaskBoss();

    /** Deletes the given task. */

    void deleteTask(List<ReadOnlyTask> targets) throws UniqueTaskList.TaskNotFoundException, IllegalValueException;

    /** Adds the given task
     * @throws IllegalValueException */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, IllegalValueException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IllegalValueException
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException, IllegalValueException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given keywords as task name
     * or task information
     **/
    void updateFilteredTaskListByKeywords(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords as start date*/
    void updateFilteredTaskListByStartDateTime(String keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords as end date*/
    void updateFilteredTaskListByEndDateTime(String keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords as category*/
    void updateFilteredTaskListByCategory(Category category);

    /** clear all tasks in the filtered task list by the given keywords as category*/
    void clearTasksByCategory(Category category) throws IllegalValueException;

    boolean hasCategory(Category t);

    //@@author A0138961W
    /** Undoes previous command of TaskBoss*/
    void undoTaskboss() throws EmptyStackException, IllegalValueException;

    String undoTaskbossInput() throws IllegalValueException;

    /** Redoes previous undo command of TaskBoss*/
    void redoTaskboss() throws EmptyStackException, IllegalValueException;

    /** Save current state of TaskBoss*/
    void saveTaskboss();

    //@@author
    /** Sorts the task list according to the provided sort type
     * @throws IllegalValueException */
    void sortTasks(SortBy sortType) throws IllegalValueException;

    //@@author A0143157J
    /** Changes the name of a category of all tasks in the filtered task list
     * @throws CommandException */
    void renameCategory(Category oldCategory, Category newCategory) throws IllegalValueException, CommandException,
        DuplicateCategoryException;

    //@@author A0144904H
    /**
     * marks tasks done if they are non recurring or updates date if it's recurring
     * @throws IllegalValueException
     */
    void markDone(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone) throws IllegalValueException;

    /**
     * marks recurring tasks done
     * @throws CommandException
     * @throws IllegalValueException
     */
    void end(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone) throws IllegalValueException,
                                                                                        CommandException;

    /**
     * unmarks a task
     * @throws CommandException
     * @throws IllegalValueException
     */
    void unmarkTask(ArrayList<Integer> indices, ArrayList<ReadOnlyTask> tasksToMarkDone) throws IllegalValueException;

}
