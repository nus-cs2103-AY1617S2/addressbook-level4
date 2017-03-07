package seedu.taskboss.commons.events.model;

import seedu.taskboss.commons.events.BaseEvent;
import seedu.taskboss.model.ReadOnlyTaskBoss;

/** Indicates the TaskBoss in the model has changed*/
public class TaskBossChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBoss data;

    public TaskBossChangedEvent(ReadOnlyTaskBoss data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size()
               + ", number of categories " + data.getCategoryList().size();
    }
}
