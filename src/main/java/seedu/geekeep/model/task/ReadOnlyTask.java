//@@author A0121658E
package seedu.geekeep.model.task;

import seedu.geekeep.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the Task Manager. Implementations should guarantee: details are
 * present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    /**
     * Formats the Task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle()).append(" Ending DateTime: ").append(getEndDateTime())
               .append(" Starting DateTime: ").append(getStartDateTime())
               .append(" Description: ").append(getDescriptoin()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    DateTime getEndDateTime();

    Description getDescriptoin();

    DateTime getStartDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the
     * task's internal tags.
     */
    UniqueTagList getTags();

    Title getTitle();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null
                        && other.isEvent()
                        && this.isEvent() // this is first to avoid NPE below
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getStartDateTime().equals(this.getStartDateTime())
                        && other.getEndDateTime().equals(this.getEndDateTime())
                        && this.isDone() == other.isDone())
                || (other != null
                        && other.isDeadline()
                        && this.isDeadline()
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getEndDateTime().equals(this.getEndDateTime())
                        && this.isDone() == other.isDone())
                || (other != null
                        && other.isFloatingTask()
                        && this.isFloatingTask()
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && this.isDone() == other.isDone());
    }


    boolean isFloatingTask();

    boolean isEvent();

    boolean isDeadline();

    boolean isDone();
}
