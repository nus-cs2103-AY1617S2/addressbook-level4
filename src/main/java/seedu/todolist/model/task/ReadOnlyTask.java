package seedu.todolist.model.task;

import seedu.todolist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    StartTime getStartTime();
    EndTime getEndTime();

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
                && (other.getStartTime() != null ? other.getStartTime().equals(this.getStartTime()) : this.getStartTime() == null)
                && (other.getEndTime() != null ? other.getEndTime().equals(this.getEndTime()) : this.getEndTime() == null)); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all details of the task.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" Start Time: " + getStartTime().toString())
               .append(" End Time: " + getEndTime().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
