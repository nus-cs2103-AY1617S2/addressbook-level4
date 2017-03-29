//@@author A0144813J
package seedu.address.model.util;

import java.util.Comparator;
import java.util.Date;

import seedu.address.model.task.ReadOnlyTask;

public class TaskDeadlineComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        Date firstDate = firstTask.getDeadline().toDeadline();
        Date secondDate = secondTask.getDeadline().toDeadline();
        if (firstDate.compareTo(secondDate) == 0) {
            String firstTitle = firstTask.getTitle().title;
            String secondTitle = secondTask.getTitle().title;
            return firstTitle.compareTo(secondTitle);
        }
        return firstDate.compareTo(secondDate);
    }
}
