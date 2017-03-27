package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.LoadCommand;

public class LoadCommandParser extends CommandParser {

    public Command parse(String args) {
        args = args.trim();
        return new LoadCommand(args);
    }

}
