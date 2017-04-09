package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

//@@author A0163673Y
/**
 * Indicates that the task list storage path has changed
 */
public class TaskListStoragePathChangedEvent extends BaseEvent {

    public final String taskListStoragePath;

    public TaskListStoragePathChangedEvent(String taskListStoragePath) {
        this.taskListStoragePath = taskListStoragePath;
    }

    @Override
    public String toString() {
        return "task list storage path changed to " + taskListStoragePath;
    }

}
