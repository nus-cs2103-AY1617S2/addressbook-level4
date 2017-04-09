package seedu.whatsleft.commons.events.ui;

import seedu.whatsleft.commons.events.BaseEvent;
import seedu.whatsleft.model.activity.ReadOnlyEvent;

//@@author A0124377A
/**
 * Indicates a request for feedback: jump to selected/added/edited event in calendar
 */
public class JumpToCalendarEventEvent extends BaseEvent {

    public ReadOnlyEvent targetEvent;

    public JumpToCalendarEventEvent(ReadOnlyEvent targetEvent) {
        this.targetEvent = targetEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
