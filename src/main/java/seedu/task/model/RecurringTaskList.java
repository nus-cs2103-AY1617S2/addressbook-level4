//package seedu.task.model;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//
//import javafx.collections.ObservableList;
//import seedu.task.commons.core.UnmodifiableObservableList;
//import seedu.task.model.tag.Tag;
//import seedu.task.model.tag.UniqueTagList;
//import seedu.task.model.task.ReadOnlyTask;
//import seedu.task.model.task.RecurringTask;
//import seedu.task.model.task.Task;
//import seedu.task.model.task.UniqueTaskList;
//import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
//
///**
// * Wraps all data at the address-book level
// * Duplicates are not allowed (by .equals comparison)
// */
//public class RecurringTaskList implements ReadOnlyTaskList {
//
//    private final UniqueTaskList recurringTasks;
//    private final UniqueTagList tags;
//
//    /*
//     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
//     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
//     *
//     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
//     *   among constructors.
//     */
//    {
//        recurringTasks = new UniqueTaskList();
//        tags = new UniqueTagList();
//    }
//
//    public RecurringTaskList() {}
//
//    /**
//     * Creates an TaskList using the Persons and Tags in the {@code toBeCopied}
//     */
//    public RecurringTaskList(ReadOnlyTaskList toBeCopied) {
//        this();
//        resetData(toBeCopied);
//    }
//
//    //// list overwrite operations
//
//    public void setTasks(List<? extends ReadOnlyTask> tasks)
//            throws UniqueTaskList.DuplicateTaskException {
//        this.recurringTasks.setTasks(tasks);
//    }
//
//    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
//        this.tags.setTags(tags);
//    }
//
//    public void resetData(ReadOnlyTaskList newData) {
//        assert newData != null;
//        try {
//            setTasks(newData.getTaskList());
//        } catch (UniqueTaskList.DuplicateTaskException e) {
//            assert false : "RecurringTaskLists should not have duplicate recurring tasks";
//        }
//        try {
//            setTags(newData.getTagList());
//        } catch (UniqueTagList.DuplicateTagException e) {
//            assert false : "RecurringTaskLists should not have duplicate tags";
//        }
//        syncMasterTagListWith(recurringTasks);
//    }
//
//    //// task-level operations
//
//    /**
//     * Adds a task to the ToDo list.
//     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
//     * and updates the Tag objects in the task to point to those in {@link #tags}.
//     *
//     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
//     */
//    public void addTask(RecurringTask p) throws UniqueTaskList.DuplicateTaskException {
//        syncMasterTagListWith(p);
//        recurringTasks.add(p);
//    }
//
//    /**
//     * Updates the task in the list at position {@code index} with {@code editedReadOnlyPerson}.
//     * {@code TaskList}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
//     * @see #syncMasterTagListWith(Task)
//     *
//     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
//     *      another existing task in the list.
//     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
//     */
//    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
//            throws UniqueTaskList.DuplicateTaskException {
//        assert editedReadOnlyTask != null;
//
//        RecurringTask editedTask = new RecurringTask(editedReadOnlyTask);
//        syncMasterTagListWith(editedTask);
//        // TODO: the tags master list will be updated even though the below line fails.
//        // This can cause the tags master list to have additional tags that are not tagged to any person
//        // in the person list.
//        recurringTasks.updateTask(index, editedTask);
//    }
//
//    /**
//     * Ensures that every tag in this task:
//     *  - exists in the master list {@link #tags}
//     *  - points to a Tag object in the master list
//     */
//    private void syncMasterTagListWith(RecurringTask p) {
//        final UniqueTagList taskTags = p.getTags();
//        tags.mergeFrom(taskTags);
//
//        // Create map with values = tag object references in the master list
//        // used for checking person tag references
//        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
//        tags.forEach(tag -> masterTagObjects.put(tag, tag));
//
//        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
//        final Set<Tag> correctTagReferences = new HashSet<>();
//        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
//        p.setTags(new UniqueTagList(correctTagReferences));
//    }
//
//    /**
//     * Ensures that every tag in these recurringTasks:
//     *  - exists in the master list {@link #tags}
//     *  - points to a Tag object in the master list
//     *  @see #syncMasterTagListWith(Task)
//     */
//    private void syncMasterTagListWith(UniqueTaskList recurringTasks) {
//        recurringTasks.forEach(this::syncMasterTagListWith);
//    }
//
//    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
//        if (recurringTasks.remove(key)) {
//            return true;
//        } else {
//            throw new UniqueTaskList.TaskNotFoundException();
//        }
//    }
//
//    //// tag-level operations
//
//    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
//        tags.add(t);
//    }
//
//    //// util methods
//
//    @Override
//    public String toString() {
//        return recurringTasks.asObservableList().size() + " recurringTasks, " + tags.asObservableList().size() +  " tags";
//        // TODO: refine later
//    }
//
//    @Override
//    public ObservableList<ReadOnlyTask> getRecurringTaskList() {
//        return new UnmodifiableObservableList<>(recurringTasks.asObservableList());
//    }
//
//    @Override
//    public ObservableList<Tag> getTagList() {
//        return new UnmodifiableObservableList<>(tags.asObservableList());
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof RecurringTaskList // instanceof handles nulls
//                        && this.recurringTasks.equals(((RecurringTaskList) other).recurringTasks)
//                        && this.tags.equalsOrderInsensitive(((RecurringTaskList) other).tags));
//    }
//
//    @Override
//    public int hashCode() {
//        // use this method for custom fields hashing instead of implementing your own
//        return Objects.hash(recurringTasks, tags);
//    }
//
//    @Override
//    public ObservableList<ReadOnlyTask> getTaskList() {
//        return null;
//    }
//
//}
