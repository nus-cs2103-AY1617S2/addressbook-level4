package org.teamstbf.yats.model.item;

import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Item in the TaskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyItem {

    Title getTitle();
    SimpleDate getDeadline();
    Schedule getTiming();
    Description getDescription();
    Periodic getPeriodic();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyItem other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getTiming().equals(this.getTiming())
                && other.getDescription().equals(this.getDescription()))
        		&& other.getPeriodic().equals(this.getPeriodic());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Title: ")
                .append(getDeadline())
                .append(" Email: ")
                .append(getTiming())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
