package onlythree.imanager.commons.events.model;

import onlythree.imanager.commons.events.BaseEvent;
import onlythree.imanager.model.ReadOnlyTaskList;

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
