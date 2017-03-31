package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ExitCommand;

public class ExitCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        // TODO Auto-generated method stub
        return new ExitCommand();
    }

}
