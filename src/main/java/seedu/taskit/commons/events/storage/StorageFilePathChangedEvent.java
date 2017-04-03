//@@author A0141011J

package seedu.taskit.commons.events.storage;

import seedu.taskit.commons.events.BaseEvent;

/** Indicates the TaskManager in the model has changed*/
public class StorageFilePathChangedEvent extends BaseEvent {

    private String filePath;

    public StorageFilePathChangedEvent(String newFilePath) {
        this.filePath = newFilePath;
    }

    public String toString() {
        return "Storage file path changed to " + filePath;
    }

    public String getPath() {
        return filePath;
    }
}
