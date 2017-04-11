package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0148081H
/**
 * Generic comparator for sorting order according to
 * status, start time, end time, then priority given in tasks
 */
public class TaskComparator implements Comparator<ReadOnlyTask> {

    public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
        int c;

        StatusComparator sc = new StatusComparator();
        c = sc.compare(t1, t2);

        if (c != 0) {
            return c;
        } else {
            StartTimeComparator stc = new StartTimeComparator();
            c = stc.compare(t1, t2);
        }

        if (c != 0) {
            return c;
        } else {
            EndTimeComparator etc = new EndTimeComparator();
            c = etc.compare(t1, t2);
        }

        if (c != 0) {
            return c;
        } else {
            PriorityComparator pc = new PriorityComparator();
            c = pc.compare(t1, t2);
        }

        return c;
    }
}
