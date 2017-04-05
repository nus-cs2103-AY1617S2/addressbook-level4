package seedu.taskmanager.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueCategoryList categories;

    /*
     * The 'unusual' code block below is an non-static initialization block,
     * sometimes used to avoid duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are
     * other ways to avoid duplication among constructors.
     */
    {
        tasks = new UniqueTaskList();
        categories = new UniqueCategoryList();
    }

    public TaskManager() {
    }

    /**
     * Creates ProcrastiNomore using the Tasks and Categories in the
     * {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setCategories(Collection<Category> Categories) throws UniqueCategoryList.DuplicateCategoryException {
        this.categories.setCategories(categories);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "ProcrastiNomores should not have duplicate tasks";
        }
        try {
            setCategories(newData.getCategoryList());
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            assert false : "ProcrastiNomores should not have duplicate categories";
        }
        syncMasterCategoryListWith(tasks);

    }

    //// task-level operations

    /**
     * Adds a task to ProcrastiNomore. Also checks the new task's categories and
     * updates {@link #categories} with any new categories found, and updates
     * the Category objects in the task to point to those in
     * {@link #categories}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(int addIndex, Task t) throws UniqueTaskList.DuplicateTaskException {
        syncMasterCategoryListWith(t);
        tasks.add(addIndex, t);
    }

    /**
     * Adds a task to ProcrastiNomore. Also checks the new task's categories and
     * updates {@link #categories} with any new categories found, and updates
     * the Category objects in the task to point to those in
     * {@link #categories}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncMasterCategoryListWith(t);
        tasks.add(t);
    }

    /**
     * Updates the task in the list at position {@code index} with
     * {@code updatedReadOnlyTask}. {@code ProcrastiNomore}'s category list will
     * be updated with the categories of {@code updatedReadOnlyTask}.
     *
     * @see #syncMasterCategoryListWith(Task)
     *
     * @throws DuplicateTaskException
     *             if updating the task's details causes the task to be
     *             equivalent to another existing task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask updatedReadOnlyTask) throws UniqueTaskList.DuplicateTaskException {
        assert updatedReadOnlyTask != null;

        Task updatedTask = new Task(updatedReadOnlyTask);
        syncMasterCategoryListWith(updatedTask);
        // TODO: the categories master list will be updated even though the
        // below line fails.
        // This can cause the categories master list to have additional
        // categories that are not categorized to any task
        // in the task list.
        tasks.updateTask(index, updatedTask);
    }

    /**
     * Ensures that every category in this task: - exists in the master list
     * {@link #categories} - points to a Category object in the master list
     */

    private void syncMasterCategoryListWith(Task task) {
        final UniqueCategoryList taskCategories = task.getCategories();
        categories.mergeFrom(taskCategories);

        // Create map with values = category object references in the master
        // list
        // used for checking task category references
        final Map<Category, Category> masterCategoryObjects = new HashMap<>();
        categories.forEach(category -> masterCategoryObjects.put(category, category));

        // Rebuild the list of task categories to point to the relevant
        // categories in the master category list.
        final Set<Category> correctCategoryReferences = new HashSet<>();
        taskCategories.forEach(category -> correctCategoryReferences.add(masterCategoryObjects.get(category)));
        task.setCategories(new UniqueCategoryList(correctCategoryReferences));
    }

    /**
     * Ensures that every category in these tasks: - exists in the master list
     * {@link #categories} - points to a Category object in the master list
     *
     * @see #syncMasterCategoryListWith(Task)
     */

    private void syncMasterCategoryListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterCategoryListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    // @@author A0139520L
    /**
     * Marks the task in the list at position {@code index} as complete.
     *
     * @see #syncMasterCategoryListWith(Task)
     *
     * @throws DuplicateTaskException
     *             if marking task as complete causes the task to be equivalent
     *             to another completed task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void markTask(int index, boolean isCompleted) throws UniqueTaskList.DuplicateTaskException {
        // TODO: the categories master list will be updated even though the
        // below line fails.
        // This can cause the categories master list to have additional
        // categories that are not categorized to any task
        // in the task list.
        tasks.markTask(index, isCompleted);
    }

    /**
     * Marks the task in the list at position {@code index} as complete.
     *
     * @see #syncMasterCategoryListWith(Task)
     *
     * @throws DuplicateTaskException
     *             if marking task as complete causes the task to be equivalent
     *             to another completed task in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    /*
     * public int isBlockedOutTime(Task t) throws
     * UniqueTaskList.DuplicateTaskException { return tasks.isBlockedOutTime(t);
     * }
     */

    // @@author
    //// tag-level operations

    public void addCategory(Category c) throws UniqueCategoryList.DuplicateCategoryException {
        categories.add(c);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + categories.asObservableList().size() + " categories";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Category> getCategoryList() {
        return new UnmodifiableObservableList<>(categories.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.tasks.equals(((TaskManager) other).tasks)
                        && this.categories.equalsOrderInsensitive(((TaskManager) other).categories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, categories);
    }
}
