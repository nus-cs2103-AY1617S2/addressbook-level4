package seedu.watodo.model.task;

import seedu.watodo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    DateTime getStartDate();
    DateTime getEndDate();
    TaskStatus getStatus();

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
                && other.getDescription().equals(this.getDescription())// state checks here onwards
               // && other.getStartDate() == this.getStartDate())
               // && other.getEndDate() == this.getEndDate()
                && other.getStatus().equals(this.getStatus())
                && other.getTags().equals(this.getTags()));
    }

    /**
     * Formats the task as text, showing all internal details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
