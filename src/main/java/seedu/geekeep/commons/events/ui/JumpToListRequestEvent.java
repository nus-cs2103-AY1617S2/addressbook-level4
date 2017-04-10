//@@author A0139438W
package seedu.geekeep.commons.events.ui;

import seedu.geekeep.commons.events.BaseEvent;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyTask targetTask;

    public JumpToListRequestEvent(int targetIndex, ReadOnlyTask targetTask) {
        this.targetIndex = targetIndex;
        this.targetTask = targetTask;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
