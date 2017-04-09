package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;


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
