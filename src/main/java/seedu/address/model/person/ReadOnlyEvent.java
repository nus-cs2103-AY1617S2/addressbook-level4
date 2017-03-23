package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Event in WhatsLeft.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Description getDescription();
    StartTime getStartTime();
    EndTime getEndTime();
    StartDate getStartDate();
    EndDate getEndDate();
    Location getLocation();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the event's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && (other.getDescription().toString() == null? this.getDescription().toString() == null :
                    other.getDescription().toString().equals(this.getDescription().toString())) // state checks here onwards
                && (other.getStartTime().toString() == null? this.getStartTime().toString() == null :
                    other.getStartTime().toString().equals(this.getStartTime().toString()))
                && (other.getEndTime().toString() == null? this.getEndTime().toString() == null :
                    other.getEndTime().toString().equals(this.getEndTime().toString()))
                && (other.getStartDate().toString() == null? this.getStartDate().toString() == null :
                    other.getStartDate().toString().equals(this.getStartDate().toString()))
                && (other.getEndDate().toString() == null? this.getEndDate().toString() == null :
                    other.getEndDate().toString().equals(this.getEndDate().toString()))
                && (other.getLocation().toString() == null? this.getLocation().toString() == null :
                    other.getLocation().toString().equals(this.getLocation().toString())));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Start Time: ")
                .append(getStartTime().toString())
                .append(" Start Date: ")
                .append(getStartDate().toString())
                .append(" End Time: ")
                .append(getEndTime().toString())
                .append(" End Date: ")
                .append(getEndDate().toString())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
