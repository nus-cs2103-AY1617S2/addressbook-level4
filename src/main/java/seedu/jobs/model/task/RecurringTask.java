package seedu.jobs.model.task;

import seedu.jobs.model.tag.UniqueTagList;

public class RecurringTask extends Task{
	
	int period;
	
	public RecurringTask(Name name, Time startTime, Time endTime, Description description, UniqueTagList tags,int period) {
		super(name, startTime, endTime, description, tags);
		this.period = period;
	}
	
	
}
