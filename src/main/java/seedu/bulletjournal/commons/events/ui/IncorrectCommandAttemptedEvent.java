package seedu.bulletjournal.commons.events.ui;

import seedu.bulletjournal.commons.events.BaseEvent;
import seedu.bulletjournal.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
