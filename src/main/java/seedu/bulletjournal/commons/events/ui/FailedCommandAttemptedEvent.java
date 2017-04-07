package seedu.bulletjournal.commons.events.ui;

import seedu.bulletjournal.commons.events.BaseEvent;

//@@author A0146738U-reused

/**
 * Indicates an attempt to execute a failed command
 */
public class FailedCommandAttemptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
