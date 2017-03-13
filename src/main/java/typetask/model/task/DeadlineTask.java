package typetask.model.task;

import java.util.Objects;

public class DeadlineTask extends Task {

    private Name name;
    private Date date;
    private Time time;

    public DeadlineTask(Name name, Date date, Time time) {
        super(name);
        this.date = date;
        this.time = time;
    }
    
    public DeadlineTask(Name name, Date date) {
        super(name);
        this.date = date;
    }
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public DeadlineTask(ReadOnlyTask source) {
        super(source);
        if (source instanceof DeadlineTask) {
            date = ((DeadlineTask) source).getDateDeadline();
            time = ((DeadlineTask) source).getTimeDeadline();
        }
    }

    public Date getDateDeadline() {
        return date;
    }

    public Time getTimeDeadline() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ReadOnlyTask && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, time);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
