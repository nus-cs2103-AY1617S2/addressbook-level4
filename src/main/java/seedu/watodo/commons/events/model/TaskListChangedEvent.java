package seedu.watodo.commons.events.model;

import seedu.watodo.commons.events.BaseEvent;
import seedu.watodo.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskListChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
