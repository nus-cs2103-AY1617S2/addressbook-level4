package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;

/** Indicates the TaskManager in the model has changed*/
public class FilePathChangedEvent extends BaseEvent {

    public final String path;

    public FilePathChangedEvent(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Request to change file path to " + path + ".";
    }
}
