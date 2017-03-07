package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of Tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
