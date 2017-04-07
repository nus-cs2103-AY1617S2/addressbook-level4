package seedu.jobs.commons.events.storage;

import seedu.jobs.commons.events.BaseEvent;

public class ConfigChangeSavePathEvent extends BaseEvent {
    
    String path;
    
    public ConfigChangeSavePathEvent(String path) {
        this.path = path;
    }
    
    
    public String getPath() {
        return path;
    }
    
    @Override
    public String toString() {
        return "Change save path in config.json";
    }
    
}
