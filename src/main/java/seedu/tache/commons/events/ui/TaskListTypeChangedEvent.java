//@@author A0142255M
package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;

/**
 * Indicates that the task list type is changed.
 */
public class TaskListTypeChangedEvent extends BaseEvent {

    public final String newTaskListType;

    public TaskListTypeChangedEvent(String newTaskListType) {
        this.newTaskListType = newTaskListType;
    }

    public String getTaskListType() {
        return newTaskListType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
