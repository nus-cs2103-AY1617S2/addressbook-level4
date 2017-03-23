package project.taskcrusher.model.task;

import java.util.Comparator;
import java.util.Date;

import project.taskcrusher.model.shared.Description;
//import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.UserItem;
import project.taskcrusher.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an active task.
 * Implementations should guarantee: details are present and not null (just empty as Optional<>),
 * field values are validated.
 */
public interface ReadOnlyTask extends UserItem {

    Priority getPriority();
    Description getDescription();
    Deadline getDeadline();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

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
        builder.append(getName());
        if (getDeadline().hasDeadline()) {
            builder.append(" " + getDeadline().toString() + " ");
        }
        if (getDescription().hasDescription()) {
            builder.append(getDescription().toString() + " ");
        }

        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    public class DeadlineComparator implements Comparator<ReadOnlyTask> {
        public int compare(ReadOnlyTask first, ReadOnlyTask second) {
            if (!first.getDeadline().hasDeadline() && !second.getDeadline().hasDeadline()) {
                return 0;
            } else if (!first.getDeadline().hasDeadline() && second.getDeadline().hasDeadline()) {
                return 1;
            } else if (first.getDeadline().hasDeadline() && !second.getDeadline().hasDeadline()) {
                return -1;
            } else { //both has deadline
                Date firstDate = first.getDeadline().getDate().get();
                assert firstDate != null;
                Date secondDate = second.getDeadline().getDate().get();
                assert secondDate != null;
                return firstDate.compareTo(secondDate);
            }
        }
    }

    public class PriorityComparator implements Comparator<ReadOnlyTask> {
        public int compare(ReadOnlyTask first, ReadOnlyTask second) {
            String firstPriority = first.getPriority().priority;
            String secondPriority = second.getPriority().priority;
            return firstPriority.compareTo(secondPriority);
        }
    }
}
