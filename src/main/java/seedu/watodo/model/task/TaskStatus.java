package seedu.watodo.model.task;

//@@author A0141077L
/**
 * Represents a Task's current status in the task manager.
 * TaskStatus can be either Undone, Ongoing, Done or Overdue.
 */
public enum TaskStatus {
    UNDONE("Undone"), ONGOING("Ongoing"), DONE("Done"), OVERDUE("Overdue");

    private final String status;

    private TaskStatus (final String text) {
        this.status = text;
    }

    @Override
    public String toString() {
        return status;
    }
}
