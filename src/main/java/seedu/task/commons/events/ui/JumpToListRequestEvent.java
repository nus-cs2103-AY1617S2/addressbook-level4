package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final String targetList;

    public JumpToListRequestEvent(String targetList, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetList = targetList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
