package seedu.jobs.model.task;




import seedu.jobs.model.tag.UniqueTagList;


public class RecurringTask extends Task{
	
	int period; //period of recurrence in days
	
	public RecurringTask(Name name, Time startTime, Time endTime, Description description, UniqueTagList tags,int period) {
		super(name, startTime, endTime, description, tags);
		this.period = period;
	}
	
	/**
	 * update the existing start time and end time by the period of recurrence
	 *
	 */
	@Override
	public void markComplete(){
		this.startTime.addDays(period);
		this.endTime.addDays(period);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	
	
}
