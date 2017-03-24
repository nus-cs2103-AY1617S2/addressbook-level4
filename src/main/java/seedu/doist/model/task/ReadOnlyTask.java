package seedu.doist.model.task;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Priority.PriorityLevel;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    Priority getPriority();
    FinishedStatus getFinishedStatus();
    Date getStartDate();
    Date getEndDate();
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this  // short circuit if same object
                || (other != null  // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription())  // state checks here onwards
                && other.getFinishedStatus().equals(this.getFinishedStatus())
                && other.getPriority().equals(this.getPriority())
                && areEqualDates(other.getStartDate(), this.getStartDate())
                && areEqualDates(other.getEndDate(), this.getEndDate()));
    }

    /**
     * Check whether 2 Date objects are equal or not
     * @return: true if both are null, or, both are not null with the same value. false otherwise.
     */
    default boolean areEqualDates(Date date1, Date date2) {
        // case 1: both are null, considered as equal
        if (date1 == null && date2 == null) {
            return true;
        }
        // case 2: both are not null and the values are equal
        if (date1 != null && date2 != null) {
            return date1.equals(date2);
        }
        return false;
    }

    /**
     * Formats the task as text, showing description and tags
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription());
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Compare the priority of two tasks
     * @return: -1 task2 has a lower priority than task1
     */
    public class ReadOnlyTaskPriorityComparator implements Comparator<ReadOnlyTask> {
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            // Highest priority to lowest priority
            PriorityLevel task1Priority = task1.getPriority().getPriorityLevel();
            PriorityLevel task2Priority = task2.getPriority().getPriorityLevel();
            return task2Priority.compareTo(task1Priority);
        }
    }

    /**
     * Combines multiple comparators together to compare tasks.
     * For example if you want to sort by end time then by priority,
     * you create a list of comparators, adding the end time comparator first
     * then adding the priority comparator.
     * @return: -1 task1 is compared to be "less" than task2 based on multiple comparators
     */
    public class CombinedComparator implements Comparator<ReadOnlyTask> {

        List<Comparator<ReadOnlyTask>> comparators;

        public CombinedComparator(List<Comparator<ReadOnlyTask>> comparators) {
            this.comparators = comparators;
        }

        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            int compareResult = 0;
            for (Comparator<ReadOnlyTask> comparator : comparators) {
                if (comparator.compare(task1, task2) != 0) {
                    return comparator.compare(task1, task2);
                }
            }
            return compareResult;
        }

    }
}
