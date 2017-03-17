package seedu.task.model.task;

import java.util.Comparator;

public class TaskComparator implements Comparator<ReadOnlyTask> {

    
    @Override
    public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
        return(Date.doesPrecede(o1.getEndDate(), o2.getEndDate()))?-1:1;
    }

}
