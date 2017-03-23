package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;

public class TaskListToShowUpdatedEvent extends BaseEvent {

    public final boolean taskListToShowEmpty;

    public TaskListToShowUpdatedEvent(boolean taskListToShowEmpty) {
        this.taskListToShowEmpty = taskListToShowEmpty;
    }

    @Override
    public String toString() {
        return "";
    }
}
