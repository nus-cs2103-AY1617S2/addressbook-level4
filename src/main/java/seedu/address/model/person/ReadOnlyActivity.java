package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Activity in WhatsLeft.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyActivity {

    Description getDescription();
    Priority getPriority();
    Location getLocation();
    StartTime getStartTime();
    EndTime getEndTime();
    FromDate getFromDate();
    ByDate getByDate();
    ToDate getToDate();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the activity's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyActivity other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getPriority().equals(this.getPriority()));
    }

    /**
     * Formats the activity as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Location: ")
                .append(getLocation())
                .append(" FromDate: ")
                .append(getFromDate())
                .append(" ToDate: ")
                .append(getToDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime())
                .append(" ByDate: ")
                .append(getByDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
