package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.UndoCommand;

//@@author A0140063X-reused
public class UndoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new UndoCommand();
    }

}
