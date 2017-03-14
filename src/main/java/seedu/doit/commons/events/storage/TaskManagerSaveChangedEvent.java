package seedu.doit.commons.events.storage;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyTaskManager;

public class TaskManagerSaveChangedEvent extends BaseEvent {
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
        return "New save location: " + this.filePath;
    }
}
