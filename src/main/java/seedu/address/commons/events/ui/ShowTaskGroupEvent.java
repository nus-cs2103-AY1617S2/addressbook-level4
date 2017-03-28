//@@author A0144885R
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to show one task group and hide the current ons.
 */
public class ShowTaskGroupEvent extends BaseEvent {

    public final String title;

    public ShowTaskGroupEvent(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
