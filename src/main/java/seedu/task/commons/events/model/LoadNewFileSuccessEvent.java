package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;
//@@author A0142939W
/** Indicates the TaskManager in the model has changed*/
public class LoadNewFileSuccessEvent extends BaseEvent {

    public final ReadOnlyTaskManager readOnlyTaskManager;

    public LoadNewFileSuccessEvent(ReadOnlyTaskManager newData) {
        this.readOnlyTaskManager = newData;
    }

    @Override
    public String toString() {
        return "Loading new file of size " + readOnlyTaskManager.getTaskList().size() + ".";
    }
}
