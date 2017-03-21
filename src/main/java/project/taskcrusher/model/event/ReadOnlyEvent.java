package project.taskcrusher.model.event;

import java.util.List;

import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an event in user inbox.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getEventName();
    List<Timeslot> getTimeslots();
    Description getDescription();
    Location getLocation();
    boolean isPast();
    boolean hasOverlappingTimeslot(Timeslot another);

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getTimeslots().equals(this.getTimeslots()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName() + " ")
                .append(getTimeslots() + " ")
                .append(getDescription() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
