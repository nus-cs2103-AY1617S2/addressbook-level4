package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;
    public final boolean shouldBackup;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data, boolean shouldBackup) {
        this.data = data;
        this.shouldBackup = shouldBackup;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
