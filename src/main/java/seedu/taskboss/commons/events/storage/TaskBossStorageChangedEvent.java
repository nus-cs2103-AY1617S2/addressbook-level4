package seedu.taskboss.commons.events.storage;

import seedu.taskboss.commons.events.BaseEvent;

//@@author A0138961W
/**
 * Indicates change in storage location for TaskBoss
 */
public class TaskBossStorageChangedEvent extends BaseEvent {

    public String newPath;

    public TaskBossStorageChangedEvent(String newPath) {
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return "taskboss.xml now saved at " + newPath;
    }

}
