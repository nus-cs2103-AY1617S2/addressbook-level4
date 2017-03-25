package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyEvent;

//@@author A0148038A
/**
 * Indicates a request to jump to the list of events
 */
public class JumpToEventListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyEvent targetEvent;

    public JumpToEventListRequestEvent(ReadOnlyEvent event, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetEvent = event;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
