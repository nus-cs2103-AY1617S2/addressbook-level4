package seedu.task.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private UniqueTaskList tasks;
    //@@author A0139161J
    private UniqueTaskList completedTasks;
    private UniqueTaskList overdueTasks;
    //@@author
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        //@@author A0139161J
        completedTasks = new UniqueTaskList();
        overdueTasks = new UniqueTaskList();
        //@@author A0139161J
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Creates a task manager using the Tasks and Tags in the {@code toBeCopied}
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
    //@@author A0139161J
    public void setCompletedTasks(List<? extends ReadOnlyTask> completedTasks)
             throws UniqueTaskList.DuplicateTaskException {
        this.completedTasks.setTasks(completedTasks);
    }

    public synchronized void setOverdueTasks(ObservableList<ReadOnlyTask> overdueTasks)
             throws UniqueTaskList.DuplicateTaskException {
        this.overdueTasks.setTasks(overdueTasks);
    }
    //@@author

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task Manager should not have duplicate tasks";
        }
        //@@author A0139161J
        try {
            setCompletedTasks(newData.getCompletedTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task Manager should not have duplicate tasks";
        }
        try {
            setOverdueTasks(newData.getOverdueTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task Manager should not have duplicate tasks";
        }
        //@@author
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "Task Manager should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(t);
        tasks.add(t);
    }

    //@@author A0139161J
    /**
     * Transfers a task to from the main list to the completed list
     *
     * @param t
     * @throws TaskNotFoundException
     * @throws DuplicateTaskException
     */
    public void transferTaskToComplete(Task t) throws TaskNotFoundException, DuplicateTaskException {
        syncMasterTagListWith(t);
        tasks.remove(t);
        completedTasks.add(t);
    }

    /**
     * Transfer a task from completed list to main list
     * when user feels that he hasn't completed the task
     *
     * @param t
     * @throws TaskNotFoundException
     * @throws DuplicateTaskException
     */
    public void transferTaskFromComplete(Task t) throws TaskNotFoundException, DuplicateTaskException {
        syncMasterTagListWith(t);
        completedTasks.remove(t);
        tasks.add(t);
    }

    /**
     * Adds a task to the task manager at specified index
     * Adapted from addTask method
     *
     * @param index
     * @param t
     * @throws UniqueTaskList.DuplicateTaskException
     */
    public void addTaskToIndex (int index, Task t) throws UniqueTaskList.DuplicateTaskException {
        //syncMasterTagListWith(t);
        tasks.addToIndex(index, t);
    }

    public void deleteCompletedTask(ReadOnlyTask t) throws TaskNotFoundException {
        try {
            completedTasks.remove(t);
        } catch (TaskNotFoundException e) {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    //@@author

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskManager}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //@@author A0139161J
    /**Usage for undo/redo command */
    public void loadTaskManagerList(UniqueTaskList tasks) {
        this.tasks.setTasks(tasks);
    }
    //@@author

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }
    //@@author A0139161J

    @Override
    public ObservableList<ReadOnlyTask> getCompletedTaskList() {
        return new UnmodifiableObservableList<>(completedTasks.asObservableList());
    }

    @Override
    public ObservableList<ReadOnlyTask> getOverdueTaskList() {
        return new UnmodifiableObservableList<>(overdueTasks.asObservableList());
    }
    //@@author

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
