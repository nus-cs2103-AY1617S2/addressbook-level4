package seedu.whatsleft.commons.events.ui;

import seedu.whatsleft.commons.events.BaseEvent;

//@@author A0148038A
/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTaskListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
