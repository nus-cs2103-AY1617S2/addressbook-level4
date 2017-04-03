package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

//@@author A0141928B
/**
 * Save current data to filePath
 */
public class ChangeStorageFilePathEvent extends BaseEvent {

    private String filePath;

    public ChangeStorageFilePathEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}
