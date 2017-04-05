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
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

import javafx.collections.ObservableList;

//@@author A0102778B

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
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
     * Creates an AddressBook using the Persons and Tags in the
     * {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
	this();
	resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyEvent> persons) throws UniqueEventList.DuplicateEventException {
	this.events.setEvents(persons);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
	this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
	assert newData != null;
	try {
	    setPersons(newData.getTaskList());
	} catch (UniqueEventList.DuplicateEventException e) {
	    assert false : "AddressBooks should not have duplicate persons";
	}
	try {
	    setTags(newData.getTagList());
	} catch (UniqueTagList.DuplicateTagException e) {
	    assert false : "AddressBooks should not have duplicate tags";
	}
	syncMasterTagListWith(events);
    }

    //// person-level operations

    /**
     * Adds a person to the address book. Also checks the new person's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueEventList.DuplicateEventException
     *             if an equivalent person already exists.
     */
    public void addEvent(Event p) throws UniqueEventList.DuplicateEventException {
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
     * Updates the person in the list at position {@code index} with
     * {@code editedReadOnlyPerson}. {@code AddressBook}'s tag list will be
     * updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @see #syncMasterTagListWith(Task)
     *
     * @throws DuplicateEventException
     *             if updating the person's details causes the person to be
     *             equivalent to another existing person in the list.
     * @throws IndexOutOfBoundsException
     *             if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(int index, ReadOnlyEvent editedReadOnlyEvent)
	    throws UniqueEventList.DuplicateEventException {
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
     * Ensures that every tag in this person: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Event p) {
	final UniqueTagList personTags = p.getTags();
	tags.mergeFrom(personTags);

	// Create map with values = tag object references in the master list
	// used for checking person tag references
	final Map<Tag, Tag> masterTagObjects = new HashMap<>();
	tags.forEach(tag -> masterTagObjects.put(tag, tag));

	// Rebuild the list of person tags to point to the relevant tags in the
	// master tag list.
	final Set<Tag> correctTagReferences = new HashSet<>();
	personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
	p.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these persons: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueEventList persons) {
	persons.forEach(this::syncMasterTagListWith);
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

    //// util methods

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
