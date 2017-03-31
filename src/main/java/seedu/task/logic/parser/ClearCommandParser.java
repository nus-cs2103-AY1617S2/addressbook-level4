package seedu.task.logic.parser;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;

public class ClearCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new ClearCommand();
    }

}
