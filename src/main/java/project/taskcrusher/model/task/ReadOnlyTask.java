package project.taskcrusher.model.task;

import java.util.Date;

import project.taskcrusher.model.shared.ReadOnlyUserToDo;

/**
 * A read-only immutable interface for an active task.
 * Implementations should guarantee: details are present and not null (just empty as Optional<>),
 * field values are validated.
 */
public interface ReadOnlyTask extends ReadOnlyUserToDo, Comparable<ReadOnlyTask> {

    Deadline getDeadline();
    boolean isOverdue(Date timer);

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals).
     * Does not check for priority equality
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + " ")
                .append(getDeadline() + " ")
                .append(getDescription() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
