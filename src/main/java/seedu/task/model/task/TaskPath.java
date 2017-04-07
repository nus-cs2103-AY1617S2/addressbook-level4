package seedu.task.model.task;

//@@author A0164061N
public class TaskPath {
    public String path;

    public TaskPath(String path) {
	this.path = path;
    }

    public String getPath() {
	return path;
    }

    @Override
    public String toString() {
	return path;
    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof TaskPath // instanceof handles nulls
			&& this.path.equals(((TaskPath) other).path)); // state
								       // check
    }

    @Override
    public int hashCode() {
	return path.hashCode();
    }
}
