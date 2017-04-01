package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask extends Comparable<ReadOnlyTask> {

    // Returns Name of Task
    Name getName();

    // Returns Completion status of Task
    boolean isDone();

    // Returns whether this task has been manually set to Today
    boolean isManualToday();

    // Returns whether this task should appear in the today task list
    boolean isToday();

    // Returns id field reserved for UI to store temporary index
    String getID();

    // Sets id field reserved for UI to store temporary index
    void setID(String id);

    // Sets animation flag
    void setAnimation(boolean flag);

    // Gets animation flag
    boolean isAnimated();

    // Returns a natural relative representation of a datetime
    String getTaskDateTime();

    // Returns a natural absolute representation of a datetime
    String getTaskAbsoluteDateTime();

    // Returns copy of Deadline of Task
    Optional<DateTime> getDeadline();

    // Returns copy of Starting Time of Task
    Optional<DateTime> getStartingTime();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    // @@author A0093999Y
    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        assert other != null;
        return other == this // short circuit if same object
                || (other.compareTo(this) == 0 && other.getName().equals(this.getName()));
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

    /**
     * Used for sorting tasks in lists
     */
    @Override
    public default int compareTo(ReadOnlyTask other) {
        DateTime minDateTime = new DateTime(new Date(Long.MIN_VALUE));
        int compareEnd = this.getDeadline().orElse(minDateTime).compareTo(other.getDeadline().orElse(minDateTime));
        int compareStart = this.getStartingTime().orElse(minDateTime)
                .compareTo(other.getStartingTime().orElse(minDateTime));

        if (compareEnd != 0) {
            return compareEnd;
        } else {
            return compareStart;
        }
    }
}
