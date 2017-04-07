//@@author A0139539R
package seedu.task.model.util;

import java.util.Comparator;
import java.util.Date;

import seedu.task.model.task.ReadOnlyTask;

public class TaskPriorityComparator implements Comparator<ReadOnlyTask> {
    @Override
    /**
     * Compares two tasks according to their priority
     * @param ReadOnlyTasks firstTask and secondTask
     * @return an integer after comparing priority
     */
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        String firstPriority = firstTask.getPriority().value;
        String secondPriority = secondTask.getPriority().value;
        if (firstPriority.compareTo(secondPriority) == 0) {
            Date firstDate = firstTask.getDeadline().toDeadline();
            Date secondDate = secondTask.getDeadline().toDeadline();
            if (firstDate.compareTo(secondDate) == 0) {
                String firstTitle = firstTask.getTitle().title;
                String secondTitle = secondTask.getTitle().title;
                return firstTitle.compareTo(secondTitle);
            }
            return firstDate.compareTo(secondDate);
        }
        return secondPriority.compareTo(firstPriority);
    }
}
