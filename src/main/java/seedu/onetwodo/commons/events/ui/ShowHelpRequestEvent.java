package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

//@@author A0141138N
/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
