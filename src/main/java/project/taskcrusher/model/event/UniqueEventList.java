package project.taskcrusher.model.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.exceptions.DuplicateDataException;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.logic.commands.MarkCommand;

//@@author A0127737X
/**
 * stores a list of events that contain no duplicates. At any point in time, {@code internalList} is sorted
 * by the earliest timeslot.
 *
 * @see Event#compareTo(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    public void sortEventsByEarliestTimeslot() {
        internalList.sort(null);
    }

    /**
     *  Checks and marks all active, incomplete events that are out-dated as overdue.
     *  Returns boolean {@code isAnyUpdate} that indicates that whether or not anything was newly marked as overdue,
     *  which can be used by the caller to tell if there is a need to overwrite data in hard disk.
     */
    public boolean updateOverdueStatus() {
        boolean isAnyUpdate = false;
        Date now = new Date();
        for (Event event: internalList) {
            if (!event.isComplete() && !event.isOverdue()) {
                isAnyUpdate = event.updateOverdueStatus(now) || isAnyUpdate;
            }
        }
        return isAnyUpdate;
    }

    /**
     * Marks the event specified by {@code targetIndex} according to {@code markFlag}.
     */
    public void markEvent(int targetIndex, int markFlag) {
        Event target = internalList.get(targetIndex);
        if (markFlag == MarkCommand.MARK_COMPLETE) {
            target.markComplete();
        } else if (markFlag == MarkCommand.MARK_INCOMPLETE) {
            target.markIncomplete();
        } else {
            assert false;
        }
        sortEventsByEarliestTimeslot();
    }

    public void confirmEventTime(int eventListIndex, int timeslotIndex) {
        internalList.get(eventListIndex).confirmTimeslot(timeslotIndex);
        sortEventsByEarliestTimeslot();
    }

//@@author

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
        sortEventsByEarliestTimeslot();
    }

    /**
     * Updates the event in the list at position {@code index} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateEvent(int index, ReadOnlyEvent editedEvent) throws DuplicateEventException {
        assert editedEvent != null;

        Event eventToUpdate = internalList.get(index);
        if (!eventToUpdate.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        eventToUpdate.resetData(editedEvent);
        internalList.set(index, eventToUpdate);
        sortEventsByEarliestTimeslot();
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
        sortEventsByEarliestTimeslot();
        return eventFoundAndDeleted;
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
        sortEventsByEarliestTimeslot();
    }

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
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
            super("Operation would result in duplicate event");
        }
    }

    /**
     * Signals that an operation targeting a specified event in the list would fail because
     * there is no such matching event in the list.
     */
    public static class EventNotFoundException extends Exception {}

}
