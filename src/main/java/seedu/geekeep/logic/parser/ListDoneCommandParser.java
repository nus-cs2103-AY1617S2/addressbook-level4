package seedu.geekeep.logic.parser;

import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.ListDoneCommand;

public class ListDoneCommandParser {
    public Command parse(String args) {
        if (!args.isEmpty()) {
            return new IncorrectCommand(ListDoneCommand.MESSAGE_USAGE);
        } else {
            return new ListDoneCommand();
        }
    }
}
