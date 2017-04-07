package project.taskcrusher.model.event;

import java.util.Date;
import java.util.List;

import project.taskcrusher.model.shared.ReadOnlyUserToDo;

/**
 * A read-only immutable interface for an event in user inbox.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent extends ReadOnlyUserToDo, Comparable<ReadOnlyEvent> {

    List<Timeslot> getTimeslots();
    Location getLocation();
    boolean isOverdue(Date timer);
    boolean hasOverlappingTimeslot(Timeslot another);
    boolean hasOverlappingEvent(List<? extends ReadOnlyEvent> preexistingEvents);
    Date getEarliestBookedTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getTimeslots().equals(this.getTimeslots()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + " ")
                .append(getTimeslots() + " ")
                .append(getDescription() + " ")
                .append(getLocation() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
