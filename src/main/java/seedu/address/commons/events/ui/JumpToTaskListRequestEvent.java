package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyTask;

//@@author A0148038A
/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyTask targetTask;

    public JumpToTaskListRequestEvent(ReadOnlyTask task, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetTask = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
