package seedu.task.model.task;

//@@author A0163845X
public class TaskCreationDateComparator implements TaskComparable {

	@Override
	public int compareTo(Task x, Task y) {
		if (x.getLocalDate().compareTo(y.getLocalDate()) < 0) {
			return -1;
		} else if (x.getLocalDate().compareTo(y.getLocalDate()) > 0) {
			return 1;
		}
		if (x.getLocalTime().compareTo(y.getLocalTime()) < 0) {
			return -1;
		}
		// assumes tasks arent made at the same time
		return 1;
	}

}
