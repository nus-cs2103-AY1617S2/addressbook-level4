package seedu.onetwodo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.onetwodo.commons.core.UnmodifiableObservableList;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 * Wraps all data at the ToDoList level Duplicates are not allowed (by .equals
 * comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

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

    public ToDoList() {
    }

    /**
     * Creates an ToDoList using the Tasks and Tags in the {@code toBeCopied}
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyToDoList newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "ToDoLists should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "ToDoLists should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //@@author A0135739W
    public boolean isEmpty() {
        return tasks.isEmpty() && tags.isEmpty();
    }

    //// task-level operations

    /**
     * Adds a task to the todo list. Also checks the new task's tags and updates
     * {@link #tags} with any new tags found, and updates the Tag objects in the
     * task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(p);
    }

    public void addTask(int internalIdx, Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(internalIdx, p);
    }

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
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

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public void sortTasks(SortOrder sortOrder, boolean isReversed) {
        tasks.sortTasks(sortOrder, isReversed);
    }

    public void doneTask(ReadOnlyTask taskToComplete) {
        tasks.done(taskToComplete);
    }

    public void undoneTask(ReadOnlyTask taskToUncomplete) {
        tasks.undone(taskToUncomplete);
    }

    //@@author A0139343E
    public Task removeRecur(ReadOnlyTask task) {
        return tasks.removeRecur(task);
    }

    public boolean contains(ReadOnlyTask task) {
        return tasks.contains(task);
    }

    public void backwardRecur(ReadOnlyTask task) {
        tasks.updateRecur(task, false);
    }

    //@@author A0135739W
    public boolean  clearDone() {
        return tasks.clearDone();
    }

    //@@author A0135739W
    public boolean clearUndone() {
        return tasks.clearUndone();
    }

    //@@author A0141138N
    public void todayTask(ReadOnlyTask taskForToday) {
        tasks.today(taskForToday);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author A0135739W
    /**
     * clears tags that are not used by any other tasks when a task is removed
     */
    void shrinkTagList(ReadOnlyTask target) {
        for (Tag tag : target.getTags()) {
            if (isUniqueTag(tag)) {
                tags.remove(tag);
            }
        }
    }

    //@@author A0135739W
    /**
     * checks if a tag is only used in one task only
     */
    private boolean isUniqueTag(Tag tagToCheck) {
        int occurrenceCount = 0;
        for (Task task : this.tasks) {
            for (Tag tagInTask : task.getTags()) {
                if (tagToCheck.equals(tagInTask)) {
                    occurrenceCount++;
                    if (occurrenceCount > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
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
                || (other instanceof ToDoList // instanceof handles nulls
                        && this.tasks.equals(((ToDoList) other).tasks)
                        && this.tags.equalsOrderInsensitive(((ToDoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags);
    }
}
