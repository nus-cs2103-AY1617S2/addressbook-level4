package seedu.address.model.task;

import java.util.Comparator;

public class PriorityComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask p1, ReadOnlyTask p2) {
        return p1.getPriority().get().getValue().compareTo(p2.getPriority().get().getValue());
    }
}
