package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.task.ReadOnlyTask;

public class TaskPriorityComparator implements Comparator<ReadOnlyTask> {
    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        String firstPriority = firstTask.getPriority().value;
        String secondPriority = secondTask.getPriority().value;
        return firstPriority.compareTo(secondPriority);
    }
}
