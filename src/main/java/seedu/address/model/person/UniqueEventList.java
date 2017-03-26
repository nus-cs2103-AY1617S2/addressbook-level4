package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.UniqueEventList.DuplicateTimeClashException;

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
    
    //@@author A0110491U
    /**
     * 
     * @param toCheck
     * @return true if the list contains an event that clashes in time with the given argument
     */
    public boolean containsTimeClash(ReadOnlyEvent toCheck) {
        assert toCheck != null;
        for (Event check : internalList) {
            LocalDateTime startdatetime;
            LocalDateTime enddatetime;
            LocalDateTime checkstartdatetime;
            LocalDateTime checkenddatetime;
            startdatetime = check.getStartDate().getValue().atTime(check.getStartTime().getValue());
            enddatetime = check.getEndDate().getValue().atTime(check.getEndTime().getValue());  
            checkstartdatetime = toCheck.getStartDate().getValue().atTime(toCheck.getStartTime().getValue());
            checkenddatetime = toCheck.getEndDate().getValue().atTime(toCheck.getEndTime().getValue());    
            if ((startdatetime.isBefore(checkenddatetime)) && (enddatetime.isAfter(checkstartdatetime))) {
                return true;
            }
        }
        return false;
    }
    //@@author
    /**
     * Adds an event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     * @throws DuplicateTimeClashException 
     */
    public void add(Event toAdd) throws DuplicateEventException, DuplicateTimeClashException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        
        if (containsTimeClash(toAdd)) {
            throw new DuplicateTimeClashException();
        }
        internalList.add(toAdd);
        internalList.sorted();
    }
    //@@author A0110491U
    /**
     * Updates the event in the list at position {@code index} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws DuplicateTimeClashException if the updating event's details clashes with another event
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(int index, ReadOnlyEvent editedEvent) throws DuplicateEventException, DuplicateTimeClashException {
        assert editedEvent != null;

        Event eventToUpdate = internalList.get(index);
        if (!eventToUpdate.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }
        Event eventToUpdateKeep = internalList.get(index);
        Event toStore = new Event(eventToUpdateKeep);
        eventToUpdate.resetData(editedEvent);
        internalList.remove(index);
        if (containsTimeClash(eventToUpdate)) {
            internalList.add(index, toStore);
            throw new DuplicateTimeClashException();
        }
        // TODO: The code below is just a workaround to notify observers of the updated event.
        // The right way is to implement observable properties in the Event class.
        // Then, EventCard should then bind its text labels to those observable properties.
        internalList.add(index, eventToUpdate);
    }
    //@@author
    
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

    public void setActivities(List<? extends ReadOnlyEvent> events) throws DuplicateEventException, DuplicateTimeClashException {
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
    
    //@@author A0110491U
    /**
     * Signals that an operation would violate the "no clashing time" property of this list
     */
    public static class DuplicateTimeClashException extends DuplicateDataException {
        protected DuplicateTimeClashException() {
            super("Operation would result in clash of event timing");
        }
    }
    //@@author
    /**
     * Signals that an operation targeting a specified event in the list would fail because
     * there is no such matching event in the list.
     */
    public static class EventNotFoundException extends Exception {}


    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException, DuplicateTimeClashException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }
}
