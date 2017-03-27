package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.HelpCommand;

public class HelpCommandParser extends CommandParser{

    private String commandHelpToShowUser;

    public Command parse(String args) {
        return new HelpCommand(args);
    }
}
