package seedu.tasklist.model.task;


/**
 * Represents a Task's status (completed/uncompleted) in the task list.
 */
public class Status {

    public static final boolean COMPLETED = true;
    public static final boolean UNCOMPLETED = false;
    public boolean status;

    public Status() {
        status = UNCOMPLETED;
    }

    public Status(boolean status) {
        this.status = status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status ? "completed" : "uncompleted";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Status status = (Status) other;
        return (this.status == status.status);
    }

}
