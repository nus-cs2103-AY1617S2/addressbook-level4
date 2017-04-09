package seedu.watodo.model.task;

import seedu.watodo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    DateTime getStartDate();
    DateTime getEndDate();
    TaskStatus getStatus();
    TaskType getTaskType();
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    //@@author A0143076J
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription())// state checks here onwards
                && other.getStatus().equals(this.getStatus())
                && other.getTags().equals(this.getTags()))
                && datesAreSame(other);
    }

    /**
     * Returns true if both have the same number of dates and the dates are equal.
     */
    default boolean datesAreSame (ReadOnlyTask other) {
        if (this.getEndDate() != null) {
            if (this.getStartDate() != null) {
                return this.getStartDate().equals(other.getStartDate())
                    && this.getEndDate().equals(other.getEndDate());
            }
            return this.getEndDate().equals(other.getEndDate())
                && this.getStartDate() == other.getStartDate();
        }
        return this.getStartDate() == other.getStartDate()
            && this.getEndDate() == other.getEndDate();
    }

    /**
     * Formats the task as text, showing all internal details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
               .append("\nTags: ");
        getTags().forEach(builder::append);
        if (this.getStartDate() != null) {
            builder.append("\nStart: ").append(this.getStartDate());
        }
        if (this.getEndDate() != null) {
            builder.append("\nBy: ").append(this.getEndDate());
        }
        return builder.toString();
    }
}
