package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0148081H
/**
 * Comparator for sorting order according to the status given in tasks
 */
public class StatusComparator implements Comparator<ReadOnlyTask> {

    public int compare(ReadOnlyTask s1, ReadOnlyTask s2) {
        return s2.getStatus().getValue().compareTo(s1.getStatus().getValue());
    }
}
