package werkbook.task.commons.events.model;

import werkbook.task.commons.events.BaseEvent;
import werkbook.task.model.ReadOnlyTaskList;

/** Indicates the TaskList in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskListChangedEvent(ReadOnlyTaskList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
