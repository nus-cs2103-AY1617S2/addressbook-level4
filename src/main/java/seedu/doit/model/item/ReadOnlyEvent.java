package seedu.doit.model.item;

import seedu.doit.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getName();

    Priority getPriority();

    EndTime getDeadline();

    Description getDescription();

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
            && other.getName().equals(this.getName()) // state checks here onwards
            && other.getPriority().equals(this.getPriority())
            && other.getDeadline().equals(this.getDeadline())
            && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Priority: ")
            .append(getPriority())
            .append(" Deadline: ")
            .append(getDeadline())
            .append(" Description: ")
            .append(getDescription())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
