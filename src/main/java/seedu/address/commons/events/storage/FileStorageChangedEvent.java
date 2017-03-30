package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0140042A
/**
 * Loads the storage file in the given file path
 */
public class FileStorageChangedEvent extends BaseEvent {

    private String filePath;

    public FileStorageChangedEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
