package savvytodo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import savvytodo.commons.core.UnmodifiableObservableList;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;
import savvytodo.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueCategoryList categories;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        categories = new UniqueCategoryList();
    }

    public TaskManager() {}

    /**
     * Creates an TaskManager using the Tasks and Categories in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setCategories(Collection<Category> categories) throws UniqueCategoryList.DuplicateCategoryException {
        this.categories.setCategories(categories);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task Manager should not have duplicate tasks";
        }
        try {
            setCategories(newData.getCategoryList());
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            assert false : "Task Manager should not have duplicate categories";
        }
        syncMasterCategoryListWith(tasks);
    }

    /**
     * Adds a task to the task manager.
     * Also checks the new task's categories and updates {@link #categories} with any new categoies found,
     * and updates the Category objects in the task to point to those in {@link #categories}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterCategoryListWith(p);
        tasks.add(p);
    }

    public void addCategory(Category t) throws UniqueCategoryList.DuplicateCategoryException {
        categories.add(t);
    }

////task-level operations

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskManager}'s category list will be updated with the categorys of {@code editedReadOnlyTask}.
     * @see #syncMasterCategoryListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterCategoryListWith(editedTask);
        // TODO: the categorys master list will be updated even though the below line fails.
        // This can cause the categorys master list to have additional categorys that are not categoryged to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
    }

////category-level operations

    /**
     * Ensures that every category in this task:
     *  - exists in the master list {@link #categories}
     *  - points to a Category object in the master list
     */
    private void syncMasterCategoryListWith(Task task) {
        final UniqueCategoryList taskCategories = task.getCategories();
        categories.mergeFrom(taskCategories);

        // Create map with values = category object references in the master list
        // used for checking task category references
        final Map<Category, Category> masterCategoryObjects = new HashMap<>();
        categories.forEach(category -> masterCategoryObjects.put(category, category));

        // Rebuild the list of task categorys to point to the relevant categorys in the master category list.
        final Set<Category> correctCategoryReferences = new HashSet<>();
        taskCategories.forEach(category -> correctCategoryReferences.add(masterCategoryObjects.get(category)));
        task.setCategories(new UniqueCategoryList(correctCategoryReferences));
    }

    /**
     * Ensures that every category in these tasks:
     *  - exists in the master list {@link #categories}
     *  - points to a Category object in the master list
     *  @see #syncMasterCategoryListWith(Task)
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

//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + categories.asObservableList().size() +  " categories";
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
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, categories);
    }
}
