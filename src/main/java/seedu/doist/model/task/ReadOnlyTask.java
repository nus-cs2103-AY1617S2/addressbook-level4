package seedu.doist.model.task;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Priority.PriorityLevel;
import seedu.doist.model.util.StringMatchUtil;

/**
 * A read-only immutable interface for a Task in the to-do list.
 * Implementations should guarantee: details are present and not null,
 * field values are validated.
 */
public interface ReadOnlyTask {

    Description getDescription();
    Priority getPriority();
    FinishedStatus getFinishedStatus();
    TaskDate getDates();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /** Function to check if task is Overdue or not **/
    public boolean isOverdue();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this  // short circuit if same object
                || (other != null  // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription())  // state checks here onwards
                && other.getFinishedStatus().equals(this.getFinishedStatus())
                && other.getPriority().equals(this.getPriority())
                && other.getDates().equals(this.getDates()));
    }

    /**
     * Formats the task as text, showing description and tags
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription());
        if (!getTags().isEmpty()) {
            builder.append(" ");
        }
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author A0140887W
    /**
     * Compare the priority of two tasks
     * @return: -1 if task2 has a lower priority than task1
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
     * Compare the timing of two tasks
     * @return: -1 if task1 is earlier than task2
     */
    public class ReadOnlyTaskTimingComparator implements Comparator<ReadOnlyTask> {
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            // Earliest to latest timing
            Date date1 = task1.getDates().getStartDate();
            Date date2 = task2.getDates().getStartDate();
            // Floating tasks are put behind
            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null) {
                return 1;
            } else if (date2 == null) {
                return -1;
            }
            return date1.compareTo(date2);
        }
    }

    /**
     * Compare the tasks by alphabetical order of their description
     * @return: -1 if task1 is less than task2 (alphabetical order)
     */
    public class ReadOnlyTaskAlphabetComparator implements Comparator<ReadOnlyTask> {
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            // A to Z
            String desc1 = task1.getDescription().desc;
            String desc2 = task2.getDescription().desc;
            return desc1.compareTo(desc2);
        }
    }

    /**
     * Compare the finished status of two tasks
     * @return: -1 if task1 is not finished but task2 is finished
     */
    public class ReadOnlyTaskFinishedStatusComparator implements Comparator<ReadOnlyTask> {
        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            FinishedStatus status1 = task1.getFinishedStatus();
            FinishedStatus status2 = task2.getFinishedStatus();
            // finished tasks are put behind
            if (status1.getIsFinished() == status2.getIsFinished()) {
                return 0;
            } else if (!status1.getIsFinished() && status2.getIsFinished()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    //@@author A0147620L
    /**
     * Compare the 'degree of closeness' of task descriptions,
     * with respect to the targetString.
     */
    public class ReadOnlyTaskMatchingComparator implements Comparator<ReadOnlyTask> {
        private String targetString;

        public ReadOnlyTaskMatchingComparator(String targetString) {
            this.targetString = targetString;
        }

        @Override
        public int compare(ReadOnlyTask task1, ReadOnlyTask task2) {
            double result1 = StringMatchUtil.matchValue(targetString, task1.getDescription().toString());
            double result2 = StringMatchUtil.matchValue(targetString, task2.getDescription().toString());
            return Double.compare(result2, result1);
        }
    }
    //@@author

    /**
     * Combines multiple comparators together to compare tasks.
     * For example if you want to sort by end time then by priority,
     * you create a list of comparators, adding the end time comparator first
     * then adding the priority comparator.
     * @return: -1 task1 is compared to be "less" than task2 based on multiple comparators
     */
    public class ReadOnlyTaskCombinedComparator implements Comparator<ReadOnlyTask> {

        private List<Comparator<ReadOnlyTask>> comparators;

        public ReadOnlyTaskCombinedComparator(List<Comparator<ReadOnlyTask>> comparators) {
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
