package seedu.address.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

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
        tags = new UniqueTagList();
    }

    public TaskManager() {
    }

    /**
     * Creates an TaskManager using the Tasks and Tags in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends Task> tasks) throws IllegalValueException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            ArrayList<Task> tasks = new ArrayList<Task>();
            for (ReadOnlyTask readOnlyTask : newData.getTaskList()) {
                tasks.add(Task.createTask(readOnlyTask));
            }
            setTasks(tasks);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "TaskManagers should not have duplicate tasks";
        } catch (IllegalValueException e) {
            assert false : "Problem resetting data";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "TaskManagers should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //// task-level operations

    /**
     * Adds a task to the task manager. Also checks the new task's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(p);
    }

    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedReadOnlyTask}. {@code TaskManager}'s tag list will be
     * updated with the tags of {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException
     *
     * @throws IllegalValueException
     *
     * @see #syncMasterTagListWith(Task)
     *
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, Task editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        // TODO: the tags master list will be updated even though the below line
        // fails.
        // This can cause the tags master list to have additional tags that are
        // not tagged to any task
        // in the task list.
        // Current idea is to redo the tag implementation.
        tasks.updateTask(index, editedTask);
        refreshMasterTagList();
    }

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the
        // master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these tasks: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    /**
     * After a tag is no longer found is in any task, ensure that it is removed
     * from the master tag list {@link #tags} - points to a Tag object in the
     * master list
     */
    private void refreshMasterTagList() {
        tags.clear();
        for (Task task : tasks) {
            for (Tag tag : task.getTags()) {
                try {
                    tags.add(tag);
                } catch (DuplicateTagException e) {
                    // Ignore
                }
            }
        }
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            refreshMasterTagList();
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags);
    }
}
