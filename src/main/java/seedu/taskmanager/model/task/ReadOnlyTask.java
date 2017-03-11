package seedu.taskmanager.model.task;

import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    StartDate getStartDate();
    EndDate getEndDate();
    Description getDescription();

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
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
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
