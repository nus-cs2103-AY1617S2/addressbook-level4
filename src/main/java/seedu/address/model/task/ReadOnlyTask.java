package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    /**
     * @return the title name of the task
     */
    Name getName();

    /**
     *
     * @return the completion status of the task
     */
    boolean isDone();

    /**
     * make the task appear in the "Today" label
     */
    void setToday();

    /**
     *
     * @return whether the task should appear in the "Today" label
     */
    boolean isToday();

    // id field reserved for UI to store temporary index
    String getID();

    void setID(String string);

    /**
     *
     * @return an enum task type: TaskWithNoDeadline, TaskWithOnlyDeadline,
     *         TaskWithDeadlineAndStartingTime, RecurringTask
     */
    TaskType getTaskType();

    /**
     *
     * @return a natural relative representation of a datetime
     */
    String getTaskDateTime();

    /**
     *
     * @return a natural absolute representation of a datetime
     */
    String getTaskAbsoluteDateTime();

    DateTime getDeadline();

    DateTime getStartingTime();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && hasSameDateTime(other)); // state
        // checks
        // here
        // onwards
    }

    default boolean hasSameDateTime(ReadOnlyTask other) {
        if ((this.getTaskType() == null
                || this.getTaskType() == TaskType.TaskWithNoDeadline)
                && (other.getTaskType() == null || other
                        .getTaskType() == TaskType.TaskWithNoDeadline)) {
            return true;
        } else if (this.getTaskType() != other.getTaskType()) {
            return false;
        } else {
            return this.getTaskAbsoluteDateTime()
                    .equals(other.getTaskAbsoluteDateTime());
        }
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Done: ").append(isDone());
        return builder.toString();
    }

    public enum TaskType {
        TaskWithNoDeadline, TaskWithOnlyDeadline, TaskWithDeadlineAndStartingTime, RecurringTask;
    }

    /**
     * Allows comparison of tasks by deadline. Tasks without deadline will be
     * deemed as the smallest
     */
    int compareTo(ReadOnlyTask task2);
}
