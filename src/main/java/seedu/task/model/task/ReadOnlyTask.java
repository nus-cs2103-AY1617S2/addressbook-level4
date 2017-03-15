package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the TaskList.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    DueDate getDueDate();
    Duration getDuration();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the tasks's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        if (other == this) {
            return true; // short circuit if same object
        }
        boolean isDescriptionEqual = (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()));
        boolean isDurationEqual =
                other != null // this is first to avoid NPE below
                && (other.getDuration() == null && this.getDuration() == null)
                || (other.getDuration() != null
                    && other.getDuration().equals(this.getDuration()));
        return isDescriptionEqual && isDurationEqual;
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Due: ");
        if (getDueDate() != null) {
            builder.append(getDueDate());
        }
        if (getDuration() != null) {
            builder.append(" " + getDuration());
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
