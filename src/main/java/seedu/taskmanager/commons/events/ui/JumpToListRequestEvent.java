package seedu.taskmanager.commons.events.ui;

import seedu.taskmanager.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final boolean isSelectCommand;

    public JumpToListRequestEvent(int targetIndex, boolean isSelectCommand) {
        this.targetIndex = targetIndex;
        this.isSelectCommand = isSelectCommand;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
