package seedu.tache.commons.events.model;

import seedu.tache.commons.events.BaseEvent;
import seedu.tache.model.ReadOnlyTaskManager;

/** Indicates the Task Manager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
