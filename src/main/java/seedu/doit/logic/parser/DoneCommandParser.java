package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }

}
