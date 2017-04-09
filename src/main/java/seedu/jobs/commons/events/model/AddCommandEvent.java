package seedu.jobs.commons.events.model;

import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.task.Task;

public class AddCommandEvent extends BaseEvent {
    
    private Task task;
    
    public AddCommandEvent(Task task) {
        this.task = task;
    }
    
    
    public Task getTask(){
        return this.task;
    }
    
    @Override
    public String toString() {
        return "Adding task : " + task;
    }
    
}
