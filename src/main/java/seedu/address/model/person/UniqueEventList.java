package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the event in the list at position {@code index} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(int index, ReadOnlyEvent editedEvent) throws DuplicateEventException {
        assert editedEvent != null;

        Event eventToUpdate = internalList.get(index);
        if (!eventToUpdate.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        eventToUpdate.resetData(editedEvent);
        // TODO: The code below is just a workaround to notify observers of the updated event.
        // The right way is to implement observable properties in the Event class.
        // Then, EventCard should then bind its text labels to those observable properties.
        internalList.set(index, eventToUpdate);
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        assert toRemove != null;
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    public void setActivities(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setActivities(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setActivities(replacement);
    }

    public UnmodifiableObservableList<Event> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateEventException extends DuplicateDataException {
        protected DuplicateEventException() {
            super("Operation would result in duplicate events");
        }
    }

    /**
     * Signals that an operation targeting a specified event in the list would fail because
     * there is no such matching event in the list.
     */
    public static class EventNotFoundException extends Exception {}

}
