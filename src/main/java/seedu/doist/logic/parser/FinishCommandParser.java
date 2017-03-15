package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.FinishCommand;
import seedu.doist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FinishCommand object
 */
public class FinishCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FinishCommand
     * and returns an FinishCommand object for execution.
     */
    public Command parse(String args) {

        int[] indices = ParserUtil.parseStringToIntArray(args);
        if (indices == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                      FinishCommand.MESSAGE_USAGE));
        }
        return new FinishCommand(indices);
    }
}
