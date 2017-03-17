package seedu.task.model.task;



/**
 * Represents a Task's completion status in the task manager.
 */
public class CompletionStatus {

    public static final String MESSAGE_COMPLETION_STATUS_CONSTRAINTS = "Edited";
    /*
     * Task is either completed or not completed, represented by a boolean status
     */
    private boolean status;

    /**
     * Sets status of CompletionStatus to argument
     */
    public CompletionStatus(boolean completionStatus) {
        this.status = completionStatus;
    }

    public void swapStatus() {
        this.status = !this.status;
    }

    public boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatus // instanceof handles nulls
                        && this.status == ((CompletionStatus) other).status); // state check
    }


}
