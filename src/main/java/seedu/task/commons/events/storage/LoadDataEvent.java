package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

//@@author A0163845X
public class LoadDataEvent extends BaseEvent {
    private String pathName;

    public LoadDataEvent(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPathName() {
        return pathName;
    }
}
