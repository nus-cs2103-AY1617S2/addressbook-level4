package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;
    public final String backupFilePath;

    // @@author A0140063X
    /**
     * This event represents the event that task manager have been modified.
     * The new data and backupFilePath to backup into if needed is contained.
     * backupFilePath can be empty is not needed.
     *
     * @param data              New taskmanager after changes.
     * @param backupFilePath    File path to back up into.
     */
    public TaskManagerChangedEvent(ReadOnlyTaskManager data, String backupFilePath) {
        assert data != null;
        this.data = data;
        this.backupFilePath = backupFilePath;
    }

    // @@author
    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
