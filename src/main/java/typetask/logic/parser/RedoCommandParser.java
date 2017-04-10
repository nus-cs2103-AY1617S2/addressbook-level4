package typetask.logic.parser;

import typetask.logic.commands.Command;
import typetask.logic.commands.RedoCommand;
//@@author A0139926R
/**
 * Creates a new RedoCommand object
 */
public class RedoCommandParser {

    public Command parse() {
        return new RedoCommand();
    }
}
