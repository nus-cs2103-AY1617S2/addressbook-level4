package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyTask;

//@@author A0124377A
/**
 * Indicates a request for feedback: jump to selected/added/edited task in calendar
 */
public class JumpToCalendarTaskEvent extends BaseEvent {

    public ReadOnlyTask targetTask;

    public JumpToCalendarTaskEvent(ReadOnlyTask targetTask) {
        this.targetTask = targetTask;
    }

    //@@author A0110491U
    public boolean canJumpTo() {
        return (this.targetTask.getByDate().value != null && this.targetTask.getByTime().value != null);
    }
    //@@author

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
