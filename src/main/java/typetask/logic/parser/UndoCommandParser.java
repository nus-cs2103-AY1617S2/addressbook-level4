package typetask.logic.parser;

import typetask.logic.commands.Command;
import typetask.logic.commands.UndoCommand;
//@@author A0139926R
/**
 * Creates a new UndoCommand object
 */
public class UndoCommandParser {

    public Command parse() {
        return new UndoCommand();
    }
}
