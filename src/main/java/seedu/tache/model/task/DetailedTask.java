package seedu.tache.model.task;

import seedu.tache.model.tag.UniqueTagList;

public class DetailedTask extends Task{
    
    private Date startDate;
    private Date endDate;
    private Time time;
    private Duration duration;
    
    public DetailedTask(Name name, UniqueTagList tags){
        super(name, tags);
    }
    
    public DetailedTask(Name name, Date startDate, Date endDate, Time time, Duration duration, UniqueTagList tags) {
        super(name, tags);
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
}
