package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0140042A
/**
 * Saves current state into the filePath
 */
public class ForceSaveEvent extends BaseEvent {

    private String filePath;

    public ForceSaveEvent(String filePath) {
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
