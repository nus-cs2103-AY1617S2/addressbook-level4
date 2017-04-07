package seedu.task.model.task;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();

    TaskDate getTaskDate();

    TaskTime getTaskStartTime();

    TaskTime getTaskEndTime();

    String getTaskDescription();

    TaskStatus getTaskStatus();

    LocalTime getLocalTime();

    LocalDate getLocalDate();

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
                        && other.getTaskName() != null && other.getTaskName().equals(this.getTaskName()) // state
                        // checks
                        // here
                        // onwards
                        && other.getTaskDate() != null && other.getTaskDate().equals(this.getTaskDate())
                        && other.getTaskStartTime() != null && other.getTaskStartTime().equals(this.getTaskStartTime())
                        && other.getTaskEndTime() != null && other.getTaskEndTime().equals(this.getTaskEndTime()))
                        && other.getTaskDescription() != null
                        && other.getTaskDescription().equals(this.getTaskDescription()) && other.getTaskStatus() != null
                        && other.getTaskStatus().equals(this.getTaskStatus());
    }

    // @@author A0146757R
    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName());
        if (getTaskDate() != null) {
            builder.append(" Date: ").append(getTaskDate().toString());
        }
        if (getTaskStartTime() != null) {
            builder.append(" Start time: ").append(getTaskStartTime());
        }
        if (getTaskEndTime() != null) {
            builder.append(" End Time: ").append(getTaskEndTime());
        }
        builder.append(" Description: " + getTaskDescription()).append(" Status: ").append(getTaskStatus());

        getTags().forEach(builder::append);
        return builder.toString();
    }
    // @@author
}
