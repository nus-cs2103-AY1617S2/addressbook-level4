package seedu.address.model.util;

import java.util.Comparator;
import java.util.Date;

import seedu.address.model.task.ReadOnlyTask;

public class TaskPriorityComparator implements Comparator<ReadOnlyTask> {
    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        String firstPriority = firstTask.getPriority().value;
        String secondPriority = secondTask.getPriority().value;
        if (firstPriority.compareTo(secondPriority) == 0) {
            Date firstDate = firstTask.getDeadline().toDeadline();
            Date secondDate = secondTask.getDeadline().toDeadline();
            return firstDate.compareTo(secondDate);
        }
        return secondPriority.compareTo(firstPriority);
    }
}
