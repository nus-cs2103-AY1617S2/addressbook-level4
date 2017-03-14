package seedu.address.model.task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task task1, Task task2) {
        return task1.getUrgencyLevel().getIntValue() - task2.getUrgencyLevel().getIntValue();
    }

}
