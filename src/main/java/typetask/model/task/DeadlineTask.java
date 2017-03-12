package typetask.model.task;

import java.util.Objects;

public class DeadlineTask extends Task{
    private Name name;
    private DateTime deadline;
    
	public DeadlineTask(Name name, DateTime deadline) {
		super(name);
		this.deadline = deadline;
	}
	
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public DeadlineTask(ReadOnlyTask source) {
    	super(source); 
        if (source instanceof DeadlineTask) {
            deadline = ((DeadlineTask) source).getDeadline();
        }
    }
	
    public DateTime getDeadline() {
        return deadline;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this 
                || (other instanceof ReadOnlyTask
                && this.isSameStateAs((ReadOnlyTask) other));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, deadline);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
