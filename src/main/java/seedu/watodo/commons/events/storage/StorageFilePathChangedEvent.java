package seedu.watodo.commons.events.storage;

import seedu.watodo.commons.events.BaseEvent;

//@@author A0141077L
/** Indicates the watodoFilePath in the Config has changed*/
public class StorageFilePathChangedEvent extends BaseEvent {

    public final String newFilePath;

    public StorageFilePathChangedEvent(String newFilePath) {
        assert newFilePath != null;
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "Storage file location moved to " + newFilePath;
    }
}
