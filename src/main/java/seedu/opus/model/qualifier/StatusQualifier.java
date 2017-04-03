package seedu.opus.model.qualifier;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the priority attribute of a task in the task manager
 */
public class StatusQualifier implements Qualifier {

    private String status;

    public StatusQualifier(String status) {
        this.status = status;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return status.equals(task.getStatus().toString());
    }
}
//@@author
