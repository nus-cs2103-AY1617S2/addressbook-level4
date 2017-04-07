package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;
import seedu.jobs.model.ReadOnlyTaskBook;

//@@author A0130979U

public class SavePathChangedEvent extends BaseEvent {
    
    private String path;
    private ReadOnlyTaskBook data;
    
    public SavePathChangedEvent(String path,ReadOnlyTaskBook data) {
        this.path = path;
        this.data = data;
    }
    
    public String getPath() {
        return path;
    }
    
    public ReadOnlyTaskBook getData(){
        return data;
    }
    
    public String toString() {
        return "changing file path to : " + path;
    }

}
//@@author
