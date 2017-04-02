//@@author A0144813J
package seedu.task.model.util;

import java.util.Comparator;

import seedu.task.model.task.ReadOnlyTask;

public class TaskTitleComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask firstTask, ReadOnlyTask secondTask) {
        String firstTitle = firstTask.getTitle().title;
        String secondTitle = secondTask.getTitle().title;
        return firstTitle.compareTo(secondTitle);
    }

}
