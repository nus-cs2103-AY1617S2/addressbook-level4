package seedu.taskboss.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;
import seedu.taskboss.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;

/**
 * Wraps all data at the taskboss level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBoss implements ReadOnlyTaskBoss {

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

    public TaskBoss() {}

    /**
     * Creates a TaskBoss using the Tasks and Categories in the {@code toBeCopied}
     * @throws IllegalValueException
     */
    public TaskBoss(ReadOnlyTaskBoss toBeCopied) throws IllegalValueException {
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

    public void resetData(ReadOnlyTaskBoss newData) throws IllegalValueException {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "TaskBoss should not have duplicate tasks";
        }
        try {
            setCategories(newData.getCategoryList());
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            assert false : "TaskBoss should not have duplicate categories";
        }
        syncMasterCategoryListWith(tasks);
    }

//// task-level operations

    /**
     * Adds a task to the taskboss.
     * Also checks the new task's categories and updates {@link #categories} with any new categories found,
     * and updates the Category objects in the task to point to those in {@link #categories}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        syncMasterCategoryListWith(task);
        tasks.add(task);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskBoss}'s category list will be updated with the categories of {@code editedReadOnlyTask}.
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
        // TODO: the categories master list will be updated even though the below line fails.
        // This can cause the categories master list to have additional categories that are not tagged to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
    }

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

        // Rebuild the list of task categories to point to the relevant categories in the master category list.
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

    //@@author A0143157J
    /**
     * Sorts tasks in TaskBoss according to these sort types:
     * - start date and time
     * - end date and time
     * @throws IllegalValueException
     */
    public void sortTasks(SortBy sortType) throws IllegalValueException {
        tasks.sort(sortType);
    }

//// category-level operations

    public void addCategory(Category t) throws IllegalValueException {
        categories.add(t);
    }

    //@@author A0147990R
    public void removeCategory(Category t) {
        categories.remove(t);
    }

    //@@authour A0143157J
    /**
     * Renames a category in TaskBoss.
     * @throws IllegalValueException
     * @throws CommandException
     */
    public void renameCategory(Category newCategory, Category oldCategory) throws IllegalValueException,
        CommandException {
        categories.replace(newCategory, oldCategory);
        tasks.renameCategory(oldCategory, newCategory);
    }

//// util methods

    //@@author
    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + categories.asObservableList().size() +  " categories";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    public ObservableList<Task> getEditableTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Category> getCategoryList() {
        return new UnmodifiableObservableList<>(categories.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBoss // instanceof handles nulls
                && this.tasks.equals(((TaskBoss) other).tasks)
                && this.categories.equalsOrderInsensitive(((TaskBoss) other).categories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, categories);
    }

}
