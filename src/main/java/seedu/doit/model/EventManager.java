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
import seedu.doit.model.item.Event;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.UniqueEventList;
import seedu.doit.model.item.UniqueEventList.DuplicateEventException;
import seedu.doit.model.item.UniqueEventList.EventNotFoundException;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Wraps all data at the event manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventManager implements ReadOnlyEventManager {

    private final UniqueEventList events;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        events = new UniqueEventList();
        tags = new UniqueTagList();
    }

    public EventManager() {
    }

    /**
     * Creates an EventManager using the Events and Tags in the {@code toBeCopied}
     */
    public EventManager(ReadOnlyEventManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setEvents(List<? extends ReadOnlyEvent> events)
        throws UniqueEventList.DuplicateEventException {
        this.events.setEvents(events);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyEventManager newData) {
        assert newData != null;
        try {
            setEvents(newData.getEventList());
        } catch (UniqueEventList.DuplicateEventException e) {
            assert false : "Event Manager should not have duplicate events";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "Event Manager should not have duplicate tags";
        }
        syncMasterTagListWith(events);
    }

//// event-level operations

    /**
     * Adds a event to the event manager.
     * Also checks the new event's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the event to point to those in {@link #tags}.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(Event p) throws UniqueEventList.DuplicateEventException {
        syncMasterTagListWith(p);
        events.add(p);
    }

    /**
     * Updates the event in the list at position {@code index} with {@code editedReadOnlyEvent}.
     * {@code EventManager}'s tag list will be updated with the tags of {@code editedReadOnlyEvent}.
     *
     * @throws DuplicateEventException    if updating the event's details causes the
     * event to be equivalent to another existing event in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     * @see #syncMasterTagListWith(Event)
     */
    public void updateEvent(int index, ReadOnlyEvent editedReadOnlyEvent)
        throws UniqueEventList.DuplicateEventException {
        assert editedReadOnlyEvent != null;

        Event editedEvent = new Event(editedReadOnlyEvent);
        syncMasterTagListWith(editedEvent);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any event
        // in the event list.
        events.updateEvent(index, editedEvent);
    }

    /**
     * Ensures that every tag in this event:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Event event) {
        final UniqueTagList eventTags = event.getTags();
        tags.mergeFrom(eventTags);

        // Create map with values = tag object references in the master list
        // used for checking event tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of event tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        eventTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        event.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these events:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Event)
     */
    private void syncMasterTagListWith(UniqueEventList events) {
        events.forEach(this::syncMasterTagListWith);
    }

    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
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
        return events.asObservableList().size() + " events, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return new UnmodifiableObservableList<>(events.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EventManager // instanceof handles nulls
            && this.events.equals(((EventManager) other).events)
            && this.tags.equalsOrderInsensitive(((EventManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events, tags);
    }
}
