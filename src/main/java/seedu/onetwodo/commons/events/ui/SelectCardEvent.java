package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;
import seedu.onetwodo.model.task.TaskType;

public class SelectCardEvent extends BaseEvent {

    public final int targetIndex;
    public final TaskType taskType;

    public SelectCardEvent(int targetIndex, TaskType taskType) {
        this.targetIndex = targetIndex;
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
