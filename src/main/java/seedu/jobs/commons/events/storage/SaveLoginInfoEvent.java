package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class SaveLoginInfoEvent extends BaseEvent{
    
    @Override
    public String toString() {
        
        return "Saving new login info";
    }
    
}
