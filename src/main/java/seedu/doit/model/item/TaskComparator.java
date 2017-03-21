package seedu.doit.model.item;

import java.util.Comparator;

public interface TaskComparator extends Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2);

}
