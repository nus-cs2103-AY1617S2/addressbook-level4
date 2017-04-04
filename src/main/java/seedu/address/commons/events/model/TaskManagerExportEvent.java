package seedu.address.commons.events.model;

import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerExportEvent extends TaskManagerChangedEvent {
    public final String path;

    public TaskManagerExportEvent(ReadOnlyTaskManager data, String path) {
        super(data);
        this.path = path;
    }
}
