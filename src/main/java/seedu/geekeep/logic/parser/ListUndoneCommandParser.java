package seedu.geekeep.logic.parser;

import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.ListUndoneCommand;

public class ListUndoneCommandParser {
    public Command parse(String args) {
        if (!args.isEmpty()) {
            return new IncorrectCommand(ListUndoneCommand.MESSAGE_USAGE);
        } else {
            return new ListUndoneCommand();
        }
    }
}
