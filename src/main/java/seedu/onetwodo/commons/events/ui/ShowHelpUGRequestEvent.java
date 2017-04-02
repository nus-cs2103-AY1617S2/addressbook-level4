//@@author A0141138N
package seedu.onetwodo.commons.events.ui;

import seedu.onetwodo.commons.events.BaseEvent;

public class ShowHelpUGRequestEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
