package seedu.doit.commons.events.storage;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyItemManager;

//@@author A0138909R
public class TaskManagerSaveChangedEvent extends BaseEvent {
    private ReadOnlyItemManager data;
    private String filePath;

    public TaskManagerSaveChangedEvent(ReadOnlyItemManager data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public ReadOnlyItemManager getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "New save location: " + this.filePath;
    }
}
