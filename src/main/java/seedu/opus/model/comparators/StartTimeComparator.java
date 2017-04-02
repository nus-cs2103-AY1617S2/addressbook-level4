package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0148081H
/**
 * Comparator for sorting order according to the start time given in tasks
 */
public class StartTimeComparator implements Comparator<ReadOnlyTask> {

    public int compare(ReadOnlyTask d1, ReadOnlyTask d2) {
        int c;

        StatusComparator sc = new StatusComparator();
        c = sc.compare(d1, d2);

        if (c != 0) {
            return c;
        } else {
            boolean bothHaveStartTime = d1.getStartTime().isPresent() && d2.getStartTime().isPresent();
            boolean oneHasStartTime = d1.getStartTime().isPresent() || d2.getStartTime().isPresent();
            if (bothHaveStartTime) {
                boolean d1IsBefore = d1.getStartTime().get().dateTime.isBefore(d2.getStartTime().get().dateTime);
                boolean d1IsAfter = d1.getStartTime().get().dateTime.isAfter(d2.getStartTime().get().dateTime);
                if (d1IsBefore) {
                    return -1;
                } else if (d1IsAfter) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (oneHasStartTime) {
                if (d1.getStartTime().isPresent()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return -1;
            }
        }
    }
}
