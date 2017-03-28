package typetask.logic.parser;

import typetask.logic.commands.Command;
import typetask.logic.commands.RedoCommand;
//@@author A0139926R

public class RedoCommandParser {

    public Command parse(String args) {
        return new RedoCommand();
    }
}
