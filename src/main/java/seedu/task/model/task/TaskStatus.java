package seedu.task.model.task;

//@@author A0146757R 

public class TaskStatus {

    public String status;

    public static final String MESSAGE_INCOMPLETE = "Ongoing";
    public static final String MESSAGE_DONE = "Completed";

    public TaskStatus(String status) {
	this.status = status;
    }

    @Override
    public String toString() {
	return status;
    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof TaskStatus // instanceof handles nulls
			&& this.status.equals(((TaskStatus) other).status)); // state
									     // check
    }

    @Override
    public int hashCode() {
	return status.hashCode();
    }
}
// @@author
