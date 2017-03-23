package seedu.watodo.model.task;

/** 
 * Represents a Task's current status in the task manager.
 * TaskStatus can be either undone, ongoing, done or overdue.
 */
public enum TaskStatus { 
    UNDONE("Undone"), ONGOING("Ongoing"), DONE("Done"), OVERDUE("Overdue"); 
  
    private final String text;
    
    private TaskStatus (final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
    
}