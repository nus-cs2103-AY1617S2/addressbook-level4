package seedu.taskmanager.commons.events.model;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;
    public final String commandText;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data, String commandText) {
        this.data = data;
        this.commandText = commandText;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
