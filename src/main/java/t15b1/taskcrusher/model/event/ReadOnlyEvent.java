package t15b1.taskcrusher.model.event;

import java.util.Optional;

import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.shared.Name;
import t15b1.taskcrusher.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an event in user inbox.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getEventName();

    EventDate getEventDate();

    Optional<Description> getDescription();

    Optional<Location> getLocation();

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
                && other.getEventDate().equals(this.getEventDate()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName() + " ")
                .append(getEventDate() + " ")
                .append(getDescription() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
