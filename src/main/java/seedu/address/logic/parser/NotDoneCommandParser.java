package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.NotDoneCommand;

/**
 * Parses input arguments and creates a new NotDoneCommand object
 */
public class NotDoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the NotDoneCommand
     * and returns an NotDoneCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        // TODO allow multiple index
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotDoneCommand.MESSAGE_USAGE));
        }

        return new NotDoneCommand(logic.parseUIIndex(args));
    }

}
