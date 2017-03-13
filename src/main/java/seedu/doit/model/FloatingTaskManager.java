package seedu.doit.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.UniqueFloatingTaskList;
import seedu.doit.model.item.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.doit.model.item.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Wraps all data at the floatingTask manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class FloatingTaskManager implements ReadOnlyFloatingTaskManager {

    private final UniqueFloatingTaskList floatingTasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        floatingTasks = new UniqueFloatingTaskList();
        tags = new UniqueTagList();
    }

    public FloatingTaskManager() {
    }

    /**
     * Creates an FloatingTaskManager using the FloatingTasks and Tags in the {@code toBeCopied}
     */
    public FloatingTaskManager(ReadOnlyFloatingTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setFloatingTasks(List<? extends ReadOnlyFloatingTask> floatingTasks)
        throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        this.floatingTasks.setFloatingTasks(floatingTasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyFloatingTaskManager newData) {
        assert newData != null;
        try {
            setFloatingTasks(newData.getFloatingTaskList());
        } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException e) {
            assert false : "FloatingTask Manager should not have duplicate floatingTasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "FloatingTask Manager should not have duplicate tags";
        }
        syncMasterTagListWith(floatingTasks);
    }

//// floatingTask-level operations

    /**
     * Adds a floatingTask to the floatingTask manager.
     * Also checks the new floatingTask's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the floatingTask to point to those in {@link #tags}.
     *
     * @throws UniqueFloatingTaskList.DuplicateFloatingTaskException if an equivalent floatingTask already exists.
     */
    public void addFloatingTask(FloatingTask p) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        syncMasterTagListWith(p);
        floatingTasks.add(p);
    }

    /**
     * Updates the floatingTask in the list at position {@code index} with {@code editedReadOnlyFloatingTask}.
     * {@code FloatingTaskManager}'s tag list will be updated with the tags of {@code editedReadOnlyFloatingTask}.
     *
     * @throws DuplicateFloatingTaskException    if updating the floatingTask's details causes the
     * floatingTask to be equivalent to another existing floatingTask in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     * @see #syncMasterTagListWith(FloatingTask)
     */
    public void updateFloatingTask(int index, ReadOnlyFloatingTask editedReadOnlyFloatingTask)
        throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        assert editedReadOnlyFloatingTask != null;

        FloatingTask editedFloatingTask = new FloatingTask(editedReadOnlyFloatingTask);
        syncMasterTagListWith(editedFloatingTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any floatingTask
        // in the floatingTask list.
        floatingTasks.updateFloatingTask(index, editedFloatingTask);
    }

    /**
     * Ensures that every tag in this floatingTask:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(FloatingTask floatingTask) {
        final UniqueTagList floatingTaskTags = floatingTask.getTags();
        tags.mergeFrom(floatingTaskTags);

        // Create map with values = tag object references in the master list
        // used for checking floatingTask tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of floatingTask tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        floatingTaskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        floatingTask.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these floatingTasks:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(FloatingTask)
     */
    private void syncMasterTagListWith(UniqueFloatingTaskList floatingTasks) {
        floatingTasks.forEach(this::syncMasterTagListWith);
    }

    public boolean removeFloatingTask(ReadOnlyFloatingTask key) throws FloatingTaskNotFoundException {
        if (floatingTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueFloatingTaskList.FloatingTaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return floatingTasks.asObservableList().size() + " floatingTasks, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyFloatingTask> getFloatingTaskList() {
        return new UnmodifiableObservableList<>(floatingTasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FloatingTaskManager // instanceof handles nulls
            && this.floatingTasks.equals(((FloatingTaskManager) other).floatingTasks)
            && this.tags.equalsOrderInsensitive(((FloatingTaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(floatingTasks, tags);
    }
}
