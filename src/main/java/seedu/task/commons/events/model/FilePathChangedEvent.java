package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.TaskManager;
//@@author A0142939W
/** Indicates the FilePath in the model has changed*/
public class FilePathChangedEvent extends BaseEvent {

    public final String path;
    public final TaskManager taskManager;

    public FilePathChangedEvent(String path, TaskManager taskManager) {
        this.path = path;
        this.taskManager = taskManager;
    }

    @Override
    public String toString() {
        return "Request to change file path to " + path + ".";
    }
}
