package seedu.task.commons.events.ui;

import java.io.File;

import seedu.task.commons.events.BaseEvent;

//@@author A0163848R-reused
/**
* Represents a request to retrieve the file at the stored path.
*/
public class LoadRequestEvent extends BaseEvent {

    private File target;

    public LoadRequestEvent(File target) {
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
