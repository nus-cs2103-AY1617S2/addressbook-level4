package seedu.doit.model.item;

import java.util.Comparator;

public interface TaskComparator extends Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask t1, ReadOnlyTask t2);

}
