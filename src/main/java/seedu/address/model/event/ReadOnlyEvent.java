package seedu.address.model.event;

import seedu.address.model.person.Description;
import seedu.address.model.person.Name;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an event in user inbox.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getEventName();
    
    Description getDescription();
    
    EventDate getEventDate();
    
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
