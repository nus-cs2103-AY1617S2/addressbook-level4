package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.HelpFormatCommand;

public class HelpFormatCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new HelpFormatCommand();
    }

}
