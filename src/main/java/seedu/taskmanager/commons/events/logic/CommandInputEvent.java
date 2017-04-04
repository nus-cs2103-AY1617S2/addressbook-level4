package seedu.taskmanager.commons.events.logic;

import seedu.taskmanager.commons.events.BaseEvent;

// @@author A0140032E
/** Indicates a valid command has been given*/
public class CommandInputEvent extends BaseEvent {
    public final String commandText;

    public CommandInputEvent(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String toString() {
        return "VALID COMMAND: " + commandText;
    }

}
