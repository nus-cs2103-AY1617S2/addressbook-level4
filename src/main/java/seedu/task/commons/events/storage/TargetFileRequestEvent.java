package seedu.task.commons.events.storage;

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

    /**
     * Get file path to redirect all further edits to
     * @return File path to redirect all further edits to
     */
    public File getTargetFile() {
        return target;
    }

    /**
     * Get user preferences to modify with target file path
     * @return User preferences to modify
     */
    public UserPrefs getUserPrefs() {
        return prefs;
    }

    @Override
    public String toString() {
        return "Targeting new YTomorrow file: " + target.toString();
    }

}
