package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();
    Deadline getDate();
    PriorityLevel getPriority();
    Information getInfo();

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
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getPriority().equals(this.getPriority())
                && other.getInfo().equals(this.getInfo()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Deadline: ")
                .append(getDate())
                .append(" Priority Level: ")
                .append(getPriority())
                .append(" Address: ")
                .append(getInfo())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
