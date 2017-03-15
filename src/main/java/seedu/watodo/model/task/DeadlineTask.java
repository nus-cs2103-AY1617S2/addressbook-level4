package seedu.watodo.model.task;

import seedu.watodo.model.tag.UniqueTagList;

/** Represents a task that has to be done by a specific deadline in the task manager.
 * * Guarantees: details are present and not null, field values are validated.
 */
public class DeadlineTask extends Task {
    
    private DateTime deadline;
    
    public DeadlineTask(Description description, DateTime dateTime, UniqueTagList tags) {
        super(description, tags);
        this.deadline = dateTime; 
    }
    
    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }
    

}
