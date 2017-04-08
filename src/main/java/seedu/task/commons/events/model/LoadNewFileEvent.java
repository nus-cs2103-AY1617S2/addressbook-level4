package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.TaskManager;
//@@author A0142939W
/** Indicates to load new task manager in the model*/
public class LoadNewFileEvent extends BaseEvent {

    public final String path;
    public final TaskManager taskManager;

    public LoadNewFileEvent(String path, TaskManager taskManager) {
        this.path = path;
        this.taskManager = taskManager;
    }

    @Override
    public String toString() {
        return "Request to change file loading path to " + path + ".";
    }
}
