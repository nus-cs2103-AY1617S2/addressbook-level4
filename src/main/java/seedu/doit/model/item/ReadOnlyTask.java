// @@author A0139399J
package seedu.doit.model.item;

import seedu.doit.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();

    Priority getPriority();

    Description getDescription();

    StartTime getStartTime();

    EndTime getDeadline();

    boolean getIsDone();

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
            || (other != null // this is first to avoid NPE below
            && other.getName().equals(this.getName()) // state checks here onwards
            && other.getPriority().equals(this.getPriority())
            && other.getDescription().equals(this.getDescription())
            && (other.getIsDone() == this.getIsDone()));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();

        appendName(builder);
        appendPriority(builder);
        appendStartTime(builder);
        appendEndTime(builder);
        appendDescription(builder);
        appendTags(builder);

        return builder.toString();
    }

    default void appendName(StringBuilder builder) {
        builder.append(getName());
    }

    default void appendPriority(StringBuilder builder) {
        builder.append(" Priority: ");
        builder.append(getPriority());
    }

    default void appendStartTime(StringBuilder builder) {
        if (hasStartTime()) {
            builder.append(" Start Time: ");
            builder.append(getStartTime());
        }
    }

    default void appendEndTime(StringBuilder builder) {
        if (hasEndTime()) {
            builder.append(" End Time: ");
            builder.append(getDeadline());
        }
    }

    default void appendDescription(StringBuilder builder) {
        builder.append(" Description: ");
        builder.append(getDescription());
    }

    default void appendTags(StringBuilder builder) {
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
    }

    boolean hasStartTime();

    boolean hasEndTime();

    int getItemType();

    boolean isTask();

    boolean isEvent();

    boolean isFloatingTask();
}
