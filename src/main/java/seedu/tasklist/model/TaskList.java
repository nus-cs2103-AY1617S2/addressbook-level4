package seedu.tasklist.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyFloatingTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList {

    private final UniqueTaskList tasks;
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
        tags = new UniqueTagList();
    }

    public TaskList() {}

    /**
     * Creates an AddressBook using the Tasks and Tags in the {@code toBeCopied}
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskList newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "FlexiTask should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "FlexiTask should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

//// task-level operations

    /**
     * Adds a task to the task list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(p);
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskList}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = null;
        String type = editedReadOnlyTask.getType();
        switch (type) {
        case FloatingTask.TYPE:
            editedTask = new FloatingTask((ReadOnlyFloatingTask) editedReadOnlyTask);
            break;
        case DeadlineTask.TYPE:
            editedTask = new DeadlineTask((ReadOnlyDeadlineTask) editedReadOnlyTask);
            break;
        case EventTask.TYPE:
            editedTask = new EventTask((ReadOnlyEventTask) editedReadOnlyTask);
            break;
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

    /**
     * Tasks are sorted according to Name in ascending order
     */
    public void sortByName() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getName().compareTo(t2.getName());
            }
        });
    }

    /**
     * Tasks are sorted according to Priority in descending order
     */
    public void sortByPriority() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getPriority().compareTo(t2.getPriority());
            }
        });
    }

    /**
     * Tasks are sorted according to Date in ascending order for event and deadline tasks,
     * and floating task listed in no particular order
     */
    public void sortByDate() {

        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                Date t1Date = new Date(Long.MAX_VALUE);
                Date t2Date = new Date(Long.MAX_VALUE);

                    switch (t1.getType()) {
                    case FloatingTask.TYPE:
                        t1Date = new Date(Long.MAX_VALUE);
                        break;
                    case DeadlineTask.TYPE:
                        t1Date = ((DeadlineTask) t1).getDeadline();
                        break;
                    case EventTask.TYPE:
                        t1Date = ((EventTask) t1).getStartDate();
                        break;
                    default:
                        break;
                    }

                    switch (t2.getType()) {
                    case FloatingTask.TYPE:
                        t2Date = new Date(Long.MAX_VALUE);
                        break;
                    case DeadlineTask.TYPE:
                        t2Date = ((DeadlineTask) t2).getDeadline();
                        break;
                    case EventTask.TYPE:
                        t2Date =  ((EventTask) t2).getStartDate();
                        break;
                    default:
                        break;
                    }
                    return t1Date.compareTo(t2Date);
            }
        });
    }
}
