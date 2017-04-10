//@@author A0142255M
package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;

/**
 * Indicates a request to view events at the calendar in a day / week / month view.
 */
public class CalendarViewRequestEvent extends BaseEvent {

    public final String view;

    public CalendarViewRequestEvent(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
