package seedu.task.model.task;

//@@author A0163845X
public class TaskStatusComparable implements TaskComparable {

    @Override
    public int compareTo(Task x, Task y) {
        return y.getTaskStatus().toString().compareTo(x.getTaskStatus().toString());
    }

}
