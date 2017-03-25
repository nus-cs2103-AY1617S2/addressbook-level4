package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed */
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;
    public final String message;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data, String message) {
        this.data = data;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
