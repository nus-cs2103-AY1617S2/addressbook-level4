package seedu.tasklist.model.task;


/**
 * Represents a Task's status (completed/uncompleted) in the task list.
 */
public class Status {

    public static final boolean COMPLETED = true;
    public static final boolean UNCOMPLETED = false;
    public boolean value;

    public Status() {
        value = UNCOMPLETED;
    }

    public Status(boolean status) {
        this.value = status;
    }

    public void setStatus(boolean status) {
        this.value = status;
    }

    @Override
    public String toString() {
        return value ? "completed" : "uncompleted";
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
        return (this.value == status.value);
    }

}
