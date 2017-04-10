//@@author A0138909R
package seedu.doit.commons.events.storage;

import java.util.Optional;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyTaskManager;

public class TaskManagerLoadChangedEvent extends BaseEvent {
    private static final String LOADED_DATA_FROM = "Loaded data from: ";
    private Optional<ReadOnlyTaskManager> data;
    private String filePath;

    public TaskManagerLoadChangedEvent(Optional<ReadOnlyTaskManager> newData, String filePath) {
        this.data = newData;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Optional<ReadOnlyTaskManager> getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return LOADED_DATA_FROM + this.filePath;
    }
}
