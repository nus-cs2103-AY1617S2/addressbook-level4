package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;
//@@author A0142939W
/**
 * An event requesting to view the help page.
 */
public class ShowHelpFormatRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
