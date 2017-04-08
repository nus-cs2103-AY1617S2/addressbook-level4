package t09b1.today.commons.events.model;

import t09b1.today.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerPathChangedEvent extends TaskManagerChangedEvent {
    public final String path;

    public TaskManagerPathChangedEvent(ReadOnlyTaskManager data, String path) {
        super(data);
        this.path = path;
    }
}
