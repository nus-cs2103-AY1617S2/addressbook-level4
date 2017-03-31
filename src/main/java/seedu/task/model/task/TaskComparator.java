package seedu.task.model.task;

import java.util.Comparator;
//@@author A0142487Y
public class TaskComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
        if ((o1.getEndDate() == null && o2.getEndDate() == null)
                || o1.getEndDate().equals(o2.getEndDate())) {
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
