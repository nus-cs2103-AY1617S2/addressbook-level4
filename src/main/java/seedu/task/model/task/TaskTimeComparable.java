package seedu.task.model.task;

//@@author A0163845X
public class TaskTimeComparable implements TaskComparable {

    @Override
    public int compareTo(Task task1, Task task2) {
        if (task1.getTaskDate() == null) {
            if (task2.getTaskDate() != null) {
                return 1;
            }
        }
        if (task2.getTaskDate() == null) {
            if (task1.getTaskDate() != null) {
                return -1;
            }
        }
        if (task1.getTaskDate() != null && task2.getTaskDate() != null) {
            if (task1.getTaskDate().compareTo(task2.getTaskDate()) > 0) {
                return -1;
            } else if (task1.getTaskDate().compareTo(task2.getTaskDate()) < 0) {
                return 1;
            }
        }
        if (task1.getTaskStartTime() == null && task1.getTaskEndTime() == null) {
            if (task2.getTaskStartTime() != null || task2.getTaskEndTime() != null) {
                return 1;
            }
            return 0;
        }
        if (task2.getTaskStartTime() == null && task2.getTaskEndTime() == null) {
            if (task1.getTaskStartTime() != null || task1.getTaskEndTime() != null) {
                return -1;
            }
            return 0;
        }
        if (task1.getTaskStartTime() != null && task2.getTaskStartTime() != null) {
            if (task1.getTaskStartTime().compareTo(task2.getTaskStartTime()) > 0) {
                return -1;
            } else if (task1.getTaskStartTime().compareTo(task2.getTaskStartTime()) < 0) {
                return 0;
            } else {
                if (task1.getTaskEndTime() == null) {
                    if (task2.getTaskEndTime() == null) {
                        return 0;
                    }
                    return 1;
                } // task1.getTaskEndTime() != null
                if (task2.getTaskEndTime() == null) {
                    return -1;
                }
                return task2.getTaskEndTime().compareTo(task1.getTaskEndTime());
            }
        }
        System.out.println("Error in tasktimecomparable");
        return 0;
        // should be unreachable

    }

}
