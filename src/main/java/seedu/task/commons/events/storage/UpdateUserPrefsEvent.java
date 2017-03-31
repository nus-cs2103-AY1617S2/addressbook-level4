package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.UserPrefs;

//@@author A0142487Y
/**
 * Indicates an exception during a file saving
 */
public class UpdateUserPrefsEvent extends BaseEvent {

    private UserPrefs userPrefs;

    public UpdateUserPrefsEvent(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    public UserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    @Override
    public String toString() {
        return userPrefs.toString();
    }

}
