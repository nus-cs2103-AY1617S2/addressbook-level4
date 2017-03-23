package seedu.address.model.util;

import java.util.Comparator;
import java.util.Date;

import seedu.address.model.task.ReadOnlyTask;

public class TaskDeadlineComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        Date firstDate = firstTask.getDeadline().toDeadline();
        Date secondDate = secondTask.getDeadline().toDeadline();
        return firstDate.compareTo(secondDate);
    }
}
