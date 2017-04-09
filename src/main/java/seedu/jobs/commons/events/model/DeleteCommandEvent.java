package seedu.jobs.commons.events.model;

import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.task.ReadOnlyTask;

public class DeleteCommandEvent extends BaseEvent{
    
    private ReadOnlyTask task;
    
    public DeleteCommandEvent(ReadOnlyTask task){
        this.task = task;
    }
    
    public ReadOnlyTask getTask(){
        return this.task;
    }
    
    @Override
    public String toString() {
        
        return "Deleting task";
    }
    
}
