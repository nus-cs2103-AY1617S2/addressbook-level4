package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

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
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotDoneCommand.MESSAGE_USAGE));
        }

        return new NotDoneCommand(index.get());
    }

}
