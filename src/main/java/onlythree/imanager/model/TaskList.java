package onlythree.imanager.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import onlythree.imanager.commons.core.UnmodifiableObservableList;
import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.IterableTaskList;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.Task;

/**
 * Wraps all data at the task list level
 * Duplicates are not allowed for tags (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList {

    private final IterableTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new IterableTaskList();
        tags = new UniqueTagList();
    }

    public TaskList() {}

    /**
     * Creates a TaskList using the Tasks and Tags in the {@code toBeCopied}
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskList newData) {
        assert newData != null;
        setTasks(newData.getTaskList());
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new AssertionError("Copying a valid TaskList should not result in duplicate tags");
        }
        syncMasterTagListWith(tasks);
    }

//// task-level operations

    //@@author A0140023E
    /**
     * Adds a task to the task list and returns the index where the task is added.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     */
    public int addTask(Task p) {
        syncMasterTagListWith(p);
        return tasks.add(p);
    }

    //@@author
    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskList}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask) {
        assert editedReadOnlyTask != null;

        Task editedTask;
        try {
            editedTask = new Task(editedReadOnlyTask);
        } catch (IllegalValueException e) {
            throw new AssertionError("Copying a valid task should always result in a valid task");
        }
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
    private void syncMasterTagListWith(IterableTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws IterableTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new IterableTaskList.TaskNotFoundException();
        }
    }

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

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.tasks.equals(((TaskList) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
