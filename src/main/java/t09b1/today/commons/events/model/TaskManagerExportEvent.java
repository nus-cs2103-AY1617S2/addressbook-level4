package t09b1.today.commons.events.model;

import t09b1.today.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerExportEvent extends TaskManagerChangedEvent {
    public final String path;

    public TaskManagerExportEvent(ReadOnlyTaskManager data, String path) {
        super(data);
        this.path = path;
    }
}
