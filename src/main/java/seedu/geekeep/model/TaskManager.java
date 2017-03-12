package seedu.geekeep.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.geekeep.commons.core.UnmodifiableObservableList;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;
import seedu.geekeep.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication between
     * constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication among
     * constructors.
     */
    {
        persons = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {
    }

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Adds a person to the address book. Also checks the new person's tags and updates {@link #tags} with any new tags
     * found, and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent person already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncMasterTagListWith(p);
        persons.add(p);
    }

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.persons.equals(((TaskManager) other).persons)
                        && this.tags.equalsOrderInsensitive(((TaskManager) other).tags));
    }

    //// person-level operations

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(persons.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    public boolean removePerson(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setPersons(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(persons);
    }

    //// tag-level operations

    public void setPersons(List<? extends ReadOnlyTask> persons) throws UniqueTaskList.DuplicateTaskException {
        this.persons.setPersons(persons);
    }

    //// util methods

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    /**
     * Ensures that every tag in this person: - exists in the master list {@link #tags} - points to a Tag object in the
     * master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList personTags = task.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these persons: - exists in the master list {@link #tags} - points to a Tag object in
     * the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    /**
     * Updates the person in the list at position {@code index} with {@code editedReadOnlyPerson}. {@code AddressBook}'s
     * tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateTaskException
     *             if updating the person's details causes the person to be equivalent to another existing person in the
     *             list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.updateTask(index, editedTask);
    }
}
