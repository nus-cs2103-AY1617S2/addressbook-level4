package seedu.tasklist.model.task;

import java.util.Date;

import seedu.tasklist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an event task (contains 2 dates) in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 *
 */
public interface ReadOnlyEventTask extends ReadOnlyTask {
    Name getName();
    Comment getComment();
    Date getStartDate();
    Date getEndDate();

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
                && other.getComment().equals(this.getComment()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Comment: ")
                .append(getComment())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
