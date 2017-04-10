package org.teamstbf.yats.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the task manager level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueEventList events;
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
        events = new UniqueEventList();
        tags = new UniqueTagList();
    }

    public TaskManager() {
    }

    /**
     * Creates an TaskManager using the Persons and Tags in the
     * {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyEvent> tasks) {
        this.events.setEvents(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;

        setPersons(newData.getTaskList());
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(events);
    }

    //// person-level operations

    /**
     * Adds a task to the task manager. Also checks the new task's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueEventList.DuplicateEventException
     *             if an equivalent person already exists.
     */
    public void addEvent(Event p) {
        storeEventTagImage();
        syncMasterTagListWith(p);
        events.add(p);
    }

    private void storeEventTagImage() {
        UniqueEventList tempEvents = new UniqueEventList();
        tempEvents.setEvents(events);
        UniqueTagList tempTags = new UniqueTagList();
        tempTags.setTags(tags);
    }

    /**
     * Updates the task in the list at position {@code index} with
     * {@code editedReadOnlyEvent}. {@code TaskManager}'s tag list will be
     * updated with the tags of {@code editedReadOnlyEvent}.
     *
     * @see #syncMasterTagListWith(Task)
     *
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(int index, ReadOnlyEvent editedReadOnlyEvent) {
        assert editedReadOnlyEvent != null;

        Event editedTask = new Event(editedReadOnlyEvent);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line
        // fails.
        // This can cause the tags master list to have additional tags that are
        // not tagged to any person
        // in the person list.
        events.updateEvent(index, editedTask);
    }

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Event p) {
        final UniqueTagList taskTags = p.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the
        // master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        p.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these tasks: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueEventList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    public boolean removeEvent(ReadOnlyEvent key) throws UniqueEventList.EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueEventList.EventNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// utility methods

    @Override
    public String toString() {
        return events.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyEvent> getTaskList() {
        return new UnmodifiableObservableList<>(events.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                        && this.events.equals(((TaskManager) other).events)
                        && this.tags.equalsOrderInsensitive(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(events, tags);
    }

}
