package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();

    Priority getPriority();

    Timing getStartTiming();

    Timing getEndTiming();

    boolean isComplete();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getDescription().equals(this.getDescription())
                        && other.getPriority().equals(this.getPriority()) && other.isComplete() == this.isComplete()
                        && other.getStartTiming().equals(this.getStartTiming())
                        && other.getEndTiming().equals(this.getEndTiming()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription()).append(" Priority: ").append(getPriority()).append(" Start Timing: ")
                .append(getStartTiming()).append(" End Timing: ").append(getEndTiming()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
