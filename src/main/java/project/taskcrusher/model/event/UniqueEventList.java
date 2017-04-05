package project.taskcrusher.model.event;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.commons.exceptions.DuplicateDataException;
import project.taskcrusher.logic.commands.MarkCommand;

/**
 * stores a list of events that contain no duplicates. At any point in time, the list is sorted
 * by the earliest timeslot.
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    //@@author A0127737X
    public void sortEventsByEarliestTimeslot() {
        internalList.sort(null);
    }

    /**
     *  Checks and marks any active events that are out-dated as overdue
     */
    public void updateOverdueStatus() {
        for (Event event: internalList) {
            if (!event.isComplete()) {
                event.updateOverdueStatus();
            }
        }
    }

    public void markEvent(int targetIndex, int markFlag) {
        Event target = internalList.get(targetIndex);
        if (markFlag == MarkCommand.MARK_COMPLETE) {
            target.markComplete();
        } else {
            target.markIncomplete();
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

    /**search through the {@code internalList} to find any events with overlapping timeslot with the {@code
     * candidate}.
     * @param candidate
     * @return {@code ObservableList} of events that have any overlapping timeslot with {@code candidate}.
     */
    public ObservableList<ReadOnlyEvent> getEventsWithOverlapingTimeslots(Timeslot candidate) {
        assert candidate != null;
        ObservableList<ReadOnlyEvent> overlappingEvents =  FXCollections.observableArrayList();

        for (Event event: internalList) {
            if (event.hasOverlappingTimeslot(candidate)) {
                overlappingEvents.add(event);
            }
        }
        return overlappingEvents;
    }

    /**
     * Adds an event to the list.
     *
     * @throws DuplicateTaskException if the event to add is a duplicate of an existing event in the list.
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
        // TODO: The code below is just a workaround to notify observers of the updated person.
        // The right way is to implement observable properties in the Person class.
        // Then, PersonCard should then bind its text labels to those observable properties.
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
