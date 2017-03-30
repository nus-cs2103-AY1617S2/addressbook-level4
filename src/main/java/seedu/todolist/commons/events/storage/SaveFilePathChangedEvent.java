package seedu.todolist.commons.events.storage;

import seedu.todolist.commons.events.BaseEvent;

/*
 * Indicates the save file location has changed.
 */
public class SaveFilePathChangedEvent extends BaseEvent {
    public final String saveFilePath;

    public SaveFilePathChangedEvent(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    @Override
    public String toString() {
        return saveFilePath;
    }
}
