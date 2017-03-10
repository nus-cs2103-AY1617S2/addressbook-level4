package seedu.jobs.model.task;

public abstract class Task {
	
	Name name;
	Time startTime;
	Time endTime;
	Address description;
	boolean completed;

	
	abstract Name getName();
	abstract Time getStartTime();
	abstract Time getEndTime();
	abstract Address getDescription();
	abstract boolean isCompleted();
	
	
	
}
