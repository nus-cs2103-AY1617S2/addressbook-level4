package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Priority getPriority();
    Status getStatus();
    Note getNote();

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
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getStatus().equals(this.getStatus())
                && other.getNote().equals(this.getNote()));
    }

    /**
     * Formats the task as text, showing all task's details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Status: ")
                .append(getStatus())
                .append(" Note: ")
                .append(getNote())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
