package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.RedoCommand;

public class RedoCommandParser extends CommandParser {

    //@@author A0140063X-reused
    @Override
    public Command parse(String args) {
        return new RedoCommand();
    }

}
