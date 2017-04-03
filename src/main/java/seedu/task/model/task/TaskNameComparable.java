package seedu.task.model.task;

public class TaskNameComparable implements TaskComparable {

	@Override
	public int compareTo(Task x, Task y) {
		return x.getTaskName().compareTo(y.getTaskName());
	}
	

}
