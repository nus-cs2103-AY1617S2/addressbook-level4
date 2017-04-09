// @@author A0139399J
package seedu.doit.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyItemManager {

    private final UniqueTaskList taskList;
    private final UniqueTagList tagList;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        taskList = new UniqueTaskList();
        tagList = new UniqueTagList();
    }

    public TaskManager() {
    }

    /**
     * Creates an TaskManager using the Tasks and Tags in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyItemManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
        throws UniqueTaskList.DuplicateTaskException {
        this.taskList.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tagList.setTags(tags);
    }

    public void resetData(ReadOnlyItemManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Task Manager should not have duplicate taskList";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "Task Manager should not have duplicate tagList";
        }
        syncMasterTagListWith(taskList);
    }

    public void setTaskComparator(Comparator<ReadOnlyTask> taskComparator) {
        taskList.setTaskComparator(taskComparator);
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tagList and updates {@link #tagList} with any new tagList found,
     * and updates the Tag objects in the task to point to those in {@link #tagList}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        taskList.add(p);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskManager}'s tag list will be updated with the tagList of {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException    if updating the task's details causes the task to be equivalent to
     *                                   another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     * @see #syncMasterTagListWith(Task)
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
        throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tagList master list will be updated even though the below line fails.
        // This can cause the tagList master list to have additional tagList that are not tagged to any task
        // in the task list.
        taskList.updateTask(index, editedTask);
    }

    /**
     * Marks an existing task in the to-do list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException
     */
    public void markTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        taskList.mark(taskIndex, taskToDone);
    }


    /**
     * Marks an existing task in the to-do list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException
     */
    public void unmarkTask(int taskIndex, ReadOnlyTask taskToDone)
            throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        taskList.unmark(taskIndex, taskToDone);
    }

    /**
     * Ensures that every tag in this task:
     * - exists in the master list {@link #tagList}
     * - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tagList.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tagList.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tagList to point to the relevant tagList in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these taskList:
     * - exists in the master list {@link #tagList}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes the equivalent task from the UniqueTaskList.
     *
     */
    public void removeTask(ReadOnlyTask task) {
        taskList.remove(task);
    }

    /**
     * Removes the equivalent taskList from the UniqueTaskList.
     *
     */
    public void removeTask(Set<ReadOnlyTask> tasks) {
        taskList.remove(tasks);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tagList.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return taskList.asObservableList().size() + " taskList, " + tagList.asObservableList().size() + " tagList";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(taskList.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tagList.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TaskManager // instanceof handles nulls
            && this.taskList.equals(((TaskManager) other).taskList)
            && this.tagList.equalsOrderInsensitive(((TaskManager) other).tagList));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskList, tagList);
    }
}
