package seedu.doist.commons.events.config;

import seedu.doist.commons.events.BaseEvent;

//@@author A0140887W
/** Indicates the absolute storage path in config has changed*/
public class AbsoluteStoragePathChangedEvent extends BaseEvent {

    public final String todoListPath;
    public final String aliasListMapPath;
    public final String userPrefsPath;

    public AbsoluteStoragePathChangedEvent(String todoListPath, String aliasListMapPath, String userPrefsPath) {
        this.todoListPath = todoListPath;
        this.aliasListMapPath = aliasListMapPath;
        this.userPrefsPath = userPrefsPath;
    }

    @Override
    public String toString() {
        return "Paths Changed: " + todoListPath + ", " + aliasListMapPath + ", " + userPrefsPath;
    }
}
