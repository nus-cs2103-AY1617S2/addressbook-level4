package seedu.tache.commons.events.ui;

import seedu.tache.commons.events.BaseEvent;

/**
 * Indicates that the detailed task count is changed.
 */
public class DetailedTaskCountChangedEvent extends BaseEvent {

    public final String message;

    public DetailedTaskCountChangedEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
