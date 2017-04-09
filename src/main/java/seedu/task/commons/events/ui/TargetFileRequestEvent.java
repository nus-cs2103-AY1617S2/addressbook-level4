package seedu.task.commons.events.ui;

import java.io.File;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.UserPrefs;

//@@author A0163848R
/**
* Represents a request to save changes to the stored path.
*/
public class TargetFileRequestEvent extends BaseEvent {

    File target;
    UserPrefs prefs;

    public TargetFileRequestEvent(File target, UserPrefs prefs) {
        this.target = target;
        this.prefs = prefs;
    }

    public File getTargetFile() {
        return target;
    }

    public UserPrefs getUserPrefs() {
        return prefs;
    }

    @Override
    public String toString() {
        return "Targeting new YTomorrow file: " + target.toString();
    }

}
