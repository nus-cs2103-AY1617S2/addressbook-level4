//@@author A0144813J
package seedu.task.model.util;

import java.util.Comparator;
import java.util.Date;

import seedu.task.model.task.ReadOnlyTask;

public class TaskDeadlineComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        Date firstDate = firstTask.getDeadline().toDeadline();
        Date secondDate = secondTask.getDeadline().toDeadline();
        if (firstDate.compareTo(secondDate) == 0) {
            String firstPriority = firstTask.getPriority().value;
            String secondPriority = secondTask.getPriority().value;
            if (firstPriority.compareTo(secondPriority) == 0) {
                String firstTitle = firstTask.getTitle().title;
                String secondTitle = secondTask.getTitle().title;
                return firstTitle.compareTo(secondTitle);
            }
            return secondPriority.compareTo(firstPriority);
        }
        return firstDate.compareTo(secondDate);
    }
}
