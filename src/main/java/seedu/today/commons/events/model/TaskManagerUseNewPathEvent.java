package seedu.today.commons.events.model;

import seedu.today.commons.events.BaseEvent;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerUseNewPathEvent extends BaseEvent {
    public final String path;

    public TaskManagerUseNewPathEvent(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "new path: " + path;
    }
}
