package seedu.whatsleft.commons.events.model;

import seedu.whatsleft.commons.events.BaseEvent;

//@@author A0121668A
/** Indicates the activity display status in the model has changed*/

public class ShowStatusChangedEvent extends BaseEvent {

    public final String displayStatus;

    public ShowStatusChangedEvent (String data) {
        this.displayStatus = data;
    }

    @Override
    public String toString() {
        return "Currently showing: " + "[" + displayStatus + "]" + " activities";
    }
}
