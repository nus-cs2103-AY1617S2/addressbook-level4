package typetask.model.task;

import java.util.Vector;

public class CompletedStatus {

    private boolean completed;
    
    //Task that is associated with the current completedStatus.
    private Task task;
    
    //Keeps a list of all tasks that are completed.
    private static Vector<Task> completedList = new Vector<Task>();
    
    public CompletedStatus(Task task) {
        this.task = task;
        this.completed = false;
    }
    
    public boolean getCompleted() {
        return this.completed;
    }
    
    /**
     * May change the name of both methods completedTrue and completedFalse
     */
    public void completedTrue() {
        this.completed = true;
        completedList.addElement(task);
    }
    
    public void completedFalse() {
        this.completed = false;
        completedList.remove(task);
    }
}
