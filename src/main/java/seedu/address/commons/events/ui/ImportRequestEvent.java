package seedu.address.commons.events.ui;

import java.io.File;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.YTomorrow;

//@@author A0163848R
/**
* Represents a request to retrieve the file at the stored path.
*/
public class ImportRequestEvent extends BaseEvent {

    private File target;
    
    public ImportRequestEvent(File target) {
        this.target = target;
    }
    
    public File getTargetFile() {
        return target;
    }

    @Override
    public String toString() {
        return "Imported YTomorrow: " + target.toString();
    }
}
