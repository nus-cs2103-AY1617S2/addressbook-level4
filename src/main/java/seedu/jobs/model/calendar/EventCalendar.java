package seedu.jobs.model.calendar;

import seedu.jobs.model.task.Task;

public class EventCalendar {
    private String summary;
    private TimeCalendar startTime;
    private TimeCalendar endTime;
    private String description;
    private IDCalendar id;
    private int period;
    
    public EventCalendar(Task task) {
    	setSummary(task.getName().toString());
    	setStartTime(new TimeCalendar(task.getStartTime()));
    	setEndTime(new TimeCalendar(task.getEndTime()));
    	setDescription(task.getDescription().toString());
    	setId(new IDCalendar(task.getName()));
    	setPeriod(task.getPeriod().value);
    }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public TimeCalendar getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeCalendar startTime) {
		this.startTime = startTime;
	}

	public TimeCalendar getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeCalendar endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IDCalendar getId() {
		return id;
	}

	public void setId(IDCalendar id) {
		this.id = id;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
    
    
}