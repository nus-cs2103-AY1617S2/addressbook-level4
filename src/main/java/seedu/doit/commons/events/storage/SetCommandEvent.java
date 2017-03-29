package seedu.doit.commons.events.storage;

import seedu.doit.commons.events.BaseEvent;

//@@author A0138909R
public class SetCommandEvent extends BaseEvent {
    private String oldCommand;
    private String newCommand;

    public SetCommandEvent(String oldCommand, String newCommand) {
        this.oldCommand = oldCommand;
        this.newCommand = newCommand;
    }

    @Override
    public String toString() {
        return "Changed " + this.oldCommand + " into " + this.newCommand;
    }

}
