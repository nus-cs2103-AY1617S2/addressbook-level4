//@@author A0146738U-reused

package seedu.bulletjournal.commons.events.model;

import seedu.bulletjournal.commons.events.BaseEvent;

/** Indicates the file path of the task master should change. */
public class FilePathChangedEvent extends BaseEvent {

    public final String newFilePath;

    public FilePathChangedEvent(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "File path changes to :" + newFilePath;
    }
}