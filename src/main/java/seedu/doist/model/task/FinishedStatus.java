package seedu.doist.model.task;

//@@author A0140887W
/**
 * Represents the finished status of a task in the to-do list
 * Default value is "not finished".
 * Will only be "finished" if user "finish" a task with the finish command
 */
public class FinishedStatus {

    private boolean isFinished;

    public FinishedStatus() {
        this(false);
    }

    public FinishedStatus(boolean status) {
        this.isFinished = status;
    }

    public boolean getIsFinished() {
        return this.isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return Boolean.toString(isFinished);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinishedStatus // instanceof handles nulls
                && this.isFinished == (((FinishedStatus) other).getIsFinished())); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isFinished);
    }

}
