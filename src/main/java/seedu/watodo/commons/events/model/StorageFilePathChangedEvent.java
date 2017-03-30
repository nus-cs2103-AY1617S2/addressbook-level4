package seedu.watodo.commons.events.model;

import seedu.watodo.commons.core.Config;
import seedu.watodo.commons.events.BaseEvent;

//@@author A0141077L
/** Indicates the watodoFilePath in the Config has changed*/
public class StorageFilePathChangedEvent extends BaseEvent {

    public final Config newConfig;

    public StorageFilePathChangedEvent(Config newConfig) {
        assert newConfig != null;
        this.newConfig = newConfig;
    }

    @Override
    public String toString() {
        return "Storage file location moved to " + newConfig.getWatodoFilePath();
    }
}
