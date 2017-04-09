package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0163848R
/**
 * An event requesting to view the help page.
 */
public class ShowThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
