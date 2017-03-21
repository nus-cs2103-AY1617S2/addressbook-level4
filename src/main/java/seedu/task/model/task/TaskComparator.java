package seedu.task.model.task;

import java.util.Comparator;

public class TaskComparator implements Comparator<ReadOnlyTask> {

    
    @Override
    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
        if ((o1.getEndDate()== null && o2.getEndDate() == null)
                ||(o1.getEndDate().equals(o2.getEndDate()))) {
            return o1.getName().fullName.compareTo(o2.getName().fullName);
        } else {
            return (Date.doesPrecede(o1.getEndDate(), o2.getEndDate())) ? -1 : 1;
        }
    }

}
