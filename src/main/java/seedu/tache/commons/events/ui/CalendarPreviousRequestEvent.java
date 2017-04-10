//@@author A0142255M
package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;

/**
 * Indicates a request to view the previous day / week / month at the calendar.
 */
public class CalendarPreviousRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
