package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;


//@@author A0164466X
/**
 * An event requesting to view themes.
 */
//@@author A0163848R
public class ShowThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
