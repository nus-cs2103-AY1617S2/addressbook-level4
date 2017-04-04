package seedu.bulletjournal.commons.events.ui;

import seedu.bulletjournal.commons.events.BaseEvent;

//@@author A0146738U-reused

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
