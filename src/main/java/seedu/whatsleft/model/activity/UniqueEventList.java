package seedu.whatsleft.model.activity;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.exceptions.DuplicateDataException;
import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.CollectionUtil;

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

    public ObservableList<Event> getInternalList() {
        return internalList;
    }

    //@@author A0148038A
    public void clearAll() {
        internalList.clear();
    }

    //@@author A0110491U
    /**
     * Checks whether there is a clashing event
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
            if (check != toCheck && (startdatetime.isBefore(checkenddatetime)) && (enddatetime.
                    isAfter(checkstartdatetime))) {
                return true;
            }
        }
        return false;
    }

    //@@author A0148038A
    /**
     * Adds an event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     * @throws DuplicateTimeClashException
     */
    public void add(Event toAdd) throws DuplicateEventException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
        internalList.sorted();
    }

    //@@author A0148038A
    /**
     * Updates an event in WhatsLeft.
     *
     * @param an event to edit and an edited event
     * @throws DuplicateEventException if the edited event is a duplicate of an existing event in the list.
     */
    public void updateEvent(Event eventToEdit, Event editedEvent) throws UniqueEventList.
        DuplicateEventException {
        assert eventToEdit != null && editedEvent != null;

        if (!eventToEdit.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }
        int index = internalList.indexOf(eventToEdit);
        internalList.set(index, editedEvent);
        internalList.sorted();
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

    public void setActivities(List<? extends ReadOnlyEvent> events) throws IllegalValueException {
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
    //@@author

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

    /**
     * Signals that two events have time clash
     * @author LiChengcheng
     *
     */
    public class DuplicateTimeClashException extends Exception {}

    public void setEvents(List<? extends ReadOnlyEvent> events) throws IllegalValueException {
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
