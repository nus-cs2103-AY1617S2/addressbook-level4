package seedu.task.model.task;

import java.util.Comparator;

//@@author A0142487Y
public class TaskComparator implements Comparator<ReadOnlyTask> {

    @Override
    /**
     * Returns -1 if the {@code o1} has and endDate strictly before {@code o2}
     * Returns -1 if the {@code o1}has no endDate and {@code o2} has an endDate
     * Returns -1 if both {@code o1} and {@code o2} have no endDates,
     * and {@code o1} has lexicographically smaller {@code fullName}
     * Returns 1 otherwise.
     */
    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
        if (o1.getEndDate().isNull() && o2.getEndDate().isNull() || (o1.getEndDate().equals(o2.getEndDate()))) {
            return o1.getName().fullName.compareTo(o2.getName().fullName);
        } else {
            if (o1.getEndDate().isNull()) {
                return 1;
            }
            if (o2.getEndDate().isNull()) {
                return -1;
            }
            return (Date.isBefore(o1.getEndDate(), o2.getEndDate())) ? -1 : 1;
        }
    }
}
