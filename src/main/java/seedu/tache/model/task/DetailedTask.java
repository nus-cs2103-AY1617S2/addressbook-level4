package seedu.tache.model.task;

import seedu.tache.model.tag.UniqueTagList;

public class DetailedTask extends Task{
    
    private Date date;
    private Time time;
    
    public DetailedTask(Name name, Date date, Time time, UniqueTagList tags) {
        super(name, tags);
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
}
