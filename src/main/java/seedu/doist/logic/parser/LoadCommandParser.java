package seedu.doist.logic.parser;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.LoadCommand;

//@@author A0140887W
/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {
    public Command parse(String argument) {
        // Remove trailing whitespace
        String processedArgument = argument.trim();
        // remove all leading spaces, new line characters etc
        processedArgument = processedArgument.replaceAll("^\\s+", "");
        // cannot be empty path
        if (processedArgument.isEmpty()) {
            return new IncorrectCommand(String.format(LoadCommand.MESSAGE_INVALID_PATH, LoadCommand.MESSAGE_USAGE));
        }
        return new LoadCommand(processedArgument);
    }
}
