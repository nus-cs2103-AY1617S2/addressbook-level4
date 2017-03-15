package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.IncorrectCommand;
import seedu.tasklist.logic.commands.LoadCommand;



/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     */
    public Command parse(String args) {
        final String[] path = args.trim().split("\\s+");
        if (path.length > 1 || path[0].isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }
        return new LoadCommand(path[0]);
    }
}
