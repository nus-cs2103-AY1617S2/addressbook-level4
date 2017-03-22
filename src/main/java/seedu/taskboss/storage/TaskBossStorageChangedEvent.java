package seedu.taskboss.storage;

import seedu.taskboss.commons.events.BaseEvent;

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
