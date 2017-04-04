package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.UnfinishCommand;

//@@author A0140887W-reused
/**
 * Parses input arguments and creates a new UnfinishCommand object
 */
public class UnfinishCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfinishCommand
     * and returns an UnfinishCommand object for execution.
     */
    public Command parse(String args) {

        int[] indices = ParserUtil.parseStringToIntArray(args);
        if (indices == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                      UnfinishCommand.MESSAGE_USAGE));
        }
        return new UnfinishCommand(indices);
    }
}
