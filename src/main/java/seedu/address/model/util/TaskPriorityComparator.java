package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.task.Task;

public class TaskPriorityComparator implements Comparator<Task>{
    @Override
    public int compare(Task firstTask, Task secondTask){
        String firstPriority = firstTask.getPriority().value;
        String secondPriority = secondTask.getPriority().value;
        return firstPriority.compareTo(secondPriority);
    }
}
