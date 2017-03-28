//@@author A0138909R
package seedu.doit.commons.events.storage;

import java.util.Optional;

import seedu.doit.commons.events.BaseEvent;
import seedu.doit.model.ReadOnlyItemManager;

public class TaskManagerLoadChangedEvent extends BaseEvent {
    private Optional<ReadOnlyItemManager> data;
    private String filePath;

    public TaskManagerLoadChangedEvent(Optional<ReadOnlyItemManager> newData, String filePath) {
        this.data = newData;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Optional<ReadOnlyItemManager> getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Loaded data from: " + this.filePath;
    }
}
