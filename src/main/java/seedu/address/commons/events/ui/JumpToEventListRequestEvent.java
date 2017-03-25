package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0148038A
/**
 * Indicates a request to jump to the list of events
 */
public class JumpToEventListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final String targetEvent;

    public JumpToEventListRequestEvent(int targetIndex, String event) {
        this.targetIndex = targetIndex;
        this.targetEvent = event;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
