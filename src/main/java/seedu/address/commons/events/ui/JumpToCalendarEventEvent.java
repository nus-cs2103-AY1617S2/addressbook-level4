package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyEvent;

//@@author A0124377A
/**
 * Indicates a request to jump in the calendar
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
