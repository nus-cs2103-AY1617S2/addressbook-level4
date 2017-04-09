package seedu.doit.commons.events.storage;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyTaskManager;

//@@author A0138909R
public class TaskManagerSaveChangedEvent extends BaseEvent {
    private static final String NEW_SAVE_LOCATION = "New save location: ";
    private ReadOnlyTaskManager data;
    private String filePath;

    public TaskManagerSaveChangedEvent(ReadOnlyTaskManager data, String filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public ReadOnlyTaskManager getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return NEW_SAVE_LOCATION + this.filePath;
    }
}
