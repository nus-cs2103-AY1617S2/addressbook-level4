package seedu.today.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.today.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask extends Comparable<ReadOnlyTask> {
    /**
     * Returns Name of Task
     * 
     * @return name of Task
     */
    Name getName();

    /**
     * Returns Completion status of Task
     * 
     * @return completion status of task
     */
    boolean isDone();

    /**
     * Returns whether this task has been manually set to Today
     * 
     * @return manual today status of task
     */
    boolean isManualToday();

    /**
     * Returns whether this task should appear in the today task list
     * 
     * @return whether task should appear in today task list
     */
    boolean isToday();

    /**
     * Returns id field reserved for UI to store temporary index
     * 
     * @return UI ID
     */
    String getID();

    /**
     * Sets id field reserved for UI to store temporary index
     * 
     * @param id
     *            UI ID
     */
    void setID(String id);

    /**
     * Sets animation flag
     * 
     * @param flag
     */
    void setAnimation(int flag);

    /**
     * Gets animation flag
     * 
     * @return animation flag
     */
    boolean isAnimated();

    /**
     * Get the animation counter
     * 
     * @return animation counter
     */
    int getIsAnimated();

    /**
     * Returns whether the task is overdue
     * 
     * @return overdue status
     */
    boolean isOverdue();

    /**
     * Returns a natural relative representation of a datetime
     * 
     * @return full relative expression of date time
     */
    String getTaskDateTime();

    /**
     * Returns a natural absolute representation of a datetime
     * 
     * @return full absolute expression of date time
     */
    String getTaskAbsoluteDateTime();

    /**
     * Returns copy of Deadline of Task
     * 
     * @return optional of deadline
     */
    Optional<DateTime> getDeadline();

    /**
     * Returns copy of Starting Time of Task
     * 
     * @return optional of starting time
     */
    Optional<DateTime> getStartingTime();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     * 
     * @return unique tag list of task
     */
    UniqueTagList getTags();

    // @@author A0093999Y
    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     * 
     * @return whether two tasks are the same name, deadline, and start time
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        assert other != null;
        return other == this // short circuit if same object
                || (other.compareTo(this) == 0 && other.getName().equals(this.getName()));
    }

    /**
     * Formats the task as text, showing all contact details.
     * 
     * @return full text representation of task
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Done: ").append(isDone());
        return builder.toString();
    }

    /**
     * Used for sorting tasks in lists
     */
    @Override
    public default int compareTo(ReadOnlyTask other) {
        DateTime minDateTime = new DateTime(new Date(Long.MIN_VALUE));
        int compareEnd = this.getDeadline().orElse(minDateTime).compareTo(other.getDeadline().orElse(minDateTime));
        int compareStart = this.getStartingTime().orElse(minDateTime)
                .compareTo(other.getStartingTime().orElse(minDateTime));
        if (this.isOverdue() && !other.isOverdue()) {
            return -1;
        } else if (!this.isOverdue() && other.isOverdue()) {
            return 1;
        } else if (compareEnd != 0) {
            return compareEnd;
        } else {
            return compareStart;
        }
    }
}
