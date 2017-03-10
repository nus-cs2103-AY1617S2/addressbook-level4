package seedu.jobs.model.task;

public abstract class Task {
	
	Name name;
	Time startTime;
	Time endTime;
	Description description;
	boolean completed;

	
	abstract Name getName();
	abstract Time getStartTime();
	abstract Time getEndTime();
	abstract Description getDescription();
	abstract boolean isCompleted();
	
	
	
}
