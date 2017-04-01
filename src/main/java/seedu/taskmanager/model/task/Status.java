package seedu.taskmanager.model.task;

/**
 * Represents a Task's status in the task manager.
 */
public class Status {
    private static final String MESSAGE_STATUS_DONE = "Done";
    private static final String MESSAGE_STATUS_NOT_DONE = "Not Done";

    public boolean value;

    /** 
     * Default constructor
     */
    public Status() {
        value = false;
    }

    /**
     * For storage
     */
    public Status(boolean status) {
        this.value = status;
    }

    public Status(String status) {
        assert status != null;
        if (status == MESSAGE_STATUS_DONE) {
            this.value = true;
        } else {
            this.value = false;
        } 
    }

    @Override
    public String toString() {
        if (value) {
            return MESSAGE_STATUS_DONE;
        } else {
            return MESSAGE_STATUS_NOT_DONE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                        && this.toString().equals(((Status) other).toString())); // state
        // check
    }
}
