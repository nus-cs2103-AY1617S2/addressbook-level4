package seedu.address.model.task;

import java.util.Comparator;

public class EndTimeComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask p, ReadOnlyTask q) {
        boolean hasStartAndEnd = p.getEndTime().isPresent() && q.getEndTime().isPresent();
        boolean hasStartOrEnd = p.getEndTime().isPresent() || q.getEndTime().isPresent();
    	if (hasStartAndEnd) {
            if (p.getEndTime().get().dateTime.isBefore(q.getEndTime().get().dateTime)) {
                return -1;
            } else if (p.getEndTime().get().dateTime.isAfter(q.getEndTime().get().dateTime)) {
                return 1;
            } else {
                return 0;
            }
        } else if (hasStartOrEnd) {
            return -1;
        } else {
            return 0;
        }
    }
}
