package seedu.address.model.task;

import java.util.Comparator;

public class PriorityComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask p1, ReadOnlyTask p2) {
        boolean bothHavePriority = p1.getPriority().isPresent() && p2.getPriority().isPresent();
        boolean oneHasPriority = p1.getPriority().isPresent() || p2.getPriority().isPresent();
        if (bothHavePriority) {
            return p1.getPriority().get().getValue().compareTo(p2.getPriority().get().getValue());
        } else if (oneHasPriority) {
            return -1;
        } else {
            return 0;
        }
    }
}
