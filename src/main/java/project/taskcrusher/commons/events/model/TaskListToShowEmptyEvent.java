package project.taskcrusher.commons.events.model;

import project.taskcrusher.commons.events.BaseEvent;
import project.taskcrusher.model.ReadOnlyUserInbox;

public class TaskListToShowEmptyEvent extends BaseEvent {

    public final ReadOnlyUserInbox data;

    public TaskListToShowEmptyEvent(ReadOnlyUserInbox data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "";
    }
}
