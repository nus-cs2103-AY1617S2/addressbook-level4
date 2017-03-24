package seedu.taskmanager.commons.events.storage;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.events.BaseEvent;

/**
 * Indicates a change in directory of the TaskManager initiated by user
 * @@author A0114269E
 */
public class TaskManagerStorageDirectoryChangedEvent extends BaseEvent {
    private final String newFilePath;
    private final Config newConfig;

    public TaskManagerStorageDirectoryChangedEvent (String newFilePath, Config newConfig) {
        this.newFilePath = newFilePath;
        this.newConfig = newConfig;
    }

    public String getNewFilePath() {
        return this.newFilePath;
    }

    public Config getNewConfig() {
        return this.newConfig;
    }

    @Override
    public String toString() {
        return "TaskManager Directory changed to " + this.newFilePath;
    }

}
