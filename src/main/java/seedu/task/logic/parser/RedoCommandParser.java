package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.RedoCommand;

//@@author A0140063X-reused
public class RedoCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new RedoCommand();
    }

}
