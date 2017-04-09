package seedu.jobs.commons.events.model;

import seedu.jobs.commons.events.BaseEvent;

public class ClearCommandEvent extends BaseEvent{
    
    public ClearCommandEvent() {
        
    }
    
    @Override
    public String toString() {
        
        return "Clearing calendar";
    }

}
