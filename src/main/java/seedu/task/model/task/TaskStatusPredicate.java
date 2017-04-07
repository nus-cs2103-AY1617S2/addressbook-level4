package seedu.task.model.task;

import java.util.function.Predicate;

public class TaskStatusPredicate implements Predicate<Object> {
    // @@author A0163845X

    private String status;

    public TaskStatusPredicate(String status) {
        this.status = status;
    }

    @Override
    public boolean test(Object arg0) {
        try {
            if (((Task) arg0).getTaskStatus().toString().toLowerCase().trim().equals(status)) {
                return true;
            }
        } catch (Exception E) {
            return false;
        }
        return false;
    }

}
