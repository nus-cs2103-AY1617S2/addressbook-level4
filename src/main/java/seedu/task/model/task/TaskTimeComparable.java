package seedu.task.model.task;

public class TaskTimeComparable {
	private TaskDate date;
	private TaskTime startTime;
	private TaskTime endTime;
	public TaskTimeComparable(Task t) {
		date = t.getTaskDate();
		startTime = t.getTaskStartTime();
		endTime = t.getTaskEndTime();
	}
	public int compareTo(TaskTimeComparable other) {
		if (this.date == null) {
			if (other.date != null) {
				return 1;
			}
		}
		if (other.date == null) {
			if (this.date != null) {
				return -1;
			}
		}
		if (this.date != null && other.date != null) {
			if (this.date.compareTo(other.date) > 0) {
				return -1;
			} else if (this.date.compareTo(other.date) < 0) {
				return 1;
			}
		}
		if (this.startTime == null && this.endTime == null) {
			if (other.startTime != null || other.endTime != null) {
				return 1;
			}
			return 0;
		}
		if (other.startTime == null && other.endTime == null) {
			if (this.startTime != null || this.endTime != null) {
				return -1;
			}
			return 0;
		}
		if (this.startTime != null && other.startTime != null) {
			if (this.startTime.compareTo(other.startTime) > 0) {
				return -1;
			} else if (this.startTime.compareTo(other.startTime) < 0) {
				return 0;
			} else {
				if (this.endTime == null) {
					if (other.endTime == null) {
						return 0;
					}
					return 1;
				} //this.endTime != null
				if (other.endTime == null) {
					return -1;
				}
				return other.endTime.compareTo(this.endTime);
			}
		}
		System.out.println("Error in tasktimecomparable");
		return 0;
		//should be unreachable
		
	}

}
