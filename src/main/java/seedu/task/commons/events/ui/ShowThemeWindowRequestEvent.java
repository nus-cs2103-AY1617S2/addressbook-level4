package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;


//@@author A0163848R
/**
 * An event indicating a request to view the Theme Window.
 */
public class ShowThemeWindowRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
