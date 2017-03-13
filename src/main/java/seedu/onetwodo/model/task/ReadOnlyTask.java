package seedu.onetwodo.model.task;

import seedu.onetwodo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the toDo list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    StartDate getStartDate();
    EndDate getEndDate();
    TaskType getTaskType();
    Description getDescription();
    boolean getDoneStatus();

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
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getTaskType().equals(this.getTaskType()))
                && other.getDescription().equals(this.getDescription());
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" StartDate: ")
                .append(getStartDate())
                .append(" EndDate: ")
                .append(getEndDate())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
