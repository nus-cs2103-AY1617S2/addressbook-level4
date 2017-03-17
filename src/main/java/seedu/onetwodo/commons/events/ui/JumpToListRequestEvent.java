package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;
import seedu.onetwodo.model.task.TaskType;

/**
 * Indicates a request to jump to the list of Tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final TaskType taskType;

    public JumpToListRequestEvent(int targetIndex, TaskType taskType) {
        this.targetIndex = targetIndex;
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
