package seedu.opus.model.qualifier;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the priority attribute of a task in the task manager
 */
public class PriorityQualifier implements Qualifier {

    private String priority;

    public PriorityQualifier(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        String priority = task.getPriority().isPresent() ?
                task.getPriority().get().toString() : "";
        return priority.equalsIgnoreCase(this.priority);
    }
}

//@@author
