package seedu.tache.model.task;

import java.util.Optional;

import seedu.tache.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Optional<DateTime> getStartDateTime();
    Optional<DateTime> getEndDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getStartDateTime())
                .append(getEndDateTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
