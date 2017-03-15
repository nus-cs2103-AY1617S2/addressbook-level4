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
               .append(" Location: ").append(getLocation()).append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    DateTime getEndDateTime();

    Location getLocation();

    DateTime getStartDateTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the
     * person's internal tags.
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
                        && other.getEndDateTime().equals(this.getEndDateTime())
                        && other.getStartDateTime().equals(this.getStartDateTime()))
                || (other != null
                        && other.isDeadline()
                        && this.isDeadline()
                        && other.getTitle().equals(this.getTitle()) // state checks here onwards
                        && other.getEndDateTime().equals(this.getEndDateTime()))
                || (other != null
                        && other.isFloatingTask()
                        && this.isFloatingTask()
                        && other.getTitle().equals(this.getTitle())); // state checks here onwards
    }


    boolean isFloatingTask();

    boolean isEvent();

    boolean isDeadline();

    boolean isDone();
}
