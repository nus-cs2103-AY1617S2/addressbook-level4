package t15b1.taskcrusher.model.task;

import java.util.Optional;

import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.shared.Name;
import t15b1.taskcrusher.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an active task.
 * Implementations should guarantee: details are present and not null (just empty as Optional<>), field values are validated.
 */
public interface ReadOnlyTask {

    Name getTaskName();
    Priority getPriority();
    Description getDescription();
    Deadline getDeadline();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals). Does not check for priority equality
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName());
        if(getDeadline().hasDeadline())
            builder.append(" " + getDeadline().toString() + " ");
        if(getDescription().hasDescription())
            builder.append(getDescription().toString() + " ");

        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
