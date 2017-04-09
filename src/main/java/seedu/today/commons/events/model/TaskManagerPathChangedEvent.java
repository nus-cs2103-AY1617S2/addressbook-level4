package seedu.today.commons.events.model;

import seedu.today.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerPathChangedEvent extends TaskManagerChangedEvent {
    public final String path;

    public TaskManagerPathChangedEvent(ReadOnlyTaskManager data, String path) {
        super(data);
        this.path = path;
    }
}
