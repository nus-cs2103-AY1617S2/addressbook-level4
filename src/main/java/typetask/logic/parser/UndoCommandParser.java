package typetask.logic.parser;

import typetask.logic.commands.Command;
import typetask.logic.commands.UndoCommand;

public class UndoCommandParser {

    public Command parse(String args) {
        return new UndoCommand();
    }
}
