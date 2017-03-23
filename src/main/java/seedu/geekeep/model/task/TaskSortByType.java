package seedu.geekeep.model.task;

import java.util.Comparator;

public class TaskSortByType implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask thisTask, ReadOnlyTask otherTask) {
        if (thisTask.isEvent() && (otherTask.isDeadline() || otherTask.isFloatingTask())) {
            return -1;
        }
        if (thisTask.isFloatingTask() && otherTask.isDeadline()) {
            return -1;
        }
        if (thisTask.isFloatingTask() && otherTask.isEvent()) {
            return 1;
        }
        if (thisTask.isDeadline() && (otherTask.isEvent() || otherTask.isFloatingTask())) {
            return 1;
        }
        return 0;
    }

}
