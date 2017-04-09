package seedu.jobs.model.calendar;

import seedu.jobs.model.task.Task;

public class EventCalendar {
    private String summary;
    private TimeCalendar startTime;
    private TimeCalendar endTime;
    private String description;
    private String period;
    
    public EventCalendar(Task task) {
    	setSummary(task.getName().toString());
    	
    	if(task.getStartTime().toString()=="") {
    		setStartTime(new TimeCalendar(task.getEndTime()));
        } else {
        	setStartTime(new TimeCalendar(task.getStartTime()));
        }
		
		if(task.getEndTime().toString()=="") {
			setEndTime(new TimeCalendar(task.getStartTime()));
        } else {
        	setEndTime(new TimeCalendar(task.getEndTime()));
        }

    	setDescription(task.getDescription().toString());
    	setPeriod(task.getPeriod().toString());
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}   
}
