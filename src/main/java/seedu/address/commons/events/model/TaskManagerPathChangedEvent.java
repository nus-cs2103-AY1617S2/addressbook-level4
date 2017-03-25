package seedu.address.commons.events.model;

import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerPathChangedEvent extends TaskManagerChangedEvent {
    public final String path;

    public TaskManagerPathChangedEvent(ReadOnlyTaskManager data, String message, String path) {
        super(data, message);
        this.path = path;
    }
}
