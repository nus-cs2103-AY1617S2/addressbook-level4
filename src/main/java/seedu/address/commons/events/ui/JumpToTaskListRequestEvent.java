package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0148038A
/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final String targetTask;

    public JumpToTaskListRequestEvent(int targetIndex, String task) {
        this.targetIndex = targetIndex;
        this.targetTask = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
