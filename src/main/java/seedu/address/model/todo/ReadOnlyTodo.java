package seedu.address.model.todo;

import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Todo in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTodo {

    Name getName();
    Date getStartTime();
    Date getEndTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the todo's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * didn't check for startTime and endTime
     */
    default boolean isSameStateAs(ReadOnlyTodo other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
                //&& other.getStartTime().equals(this.getStartTime())
                //&& other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the todo as text, showing all todo details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getName());

        if (getStartTime() != null) {
            builder.append(" Start: ")
                .append(getStartTime());
        }

        if (getEndTime() != null) {
            builder.append(" End: ")
                .append(getEndTime());
        }

        builder.append(" Tags: ");

        getTags().forEach(builder::append);
        return builder.toString();
    }
}
