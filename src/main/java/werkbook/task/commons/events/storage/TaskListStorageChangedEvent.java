//@@author A0162266E
package werkbook.task.commons.events.storage;

import werkbook.task.commons.events.BaseEvent;

/**
 * Indicates the storage location of the TaskList has changed
 */
public class TaskListStorageChangedEvent extends BaseEvent {

    public String newPath;

    public TaskListStorageChangedEvent(String newPath) {
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return "tasklist.xml now saved at " + newPath;
    }

}
