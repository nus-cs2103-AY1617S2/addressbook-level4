package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskManager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getDate();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

