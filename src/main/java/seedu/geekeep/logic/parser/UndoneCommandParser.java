package seedu.geekeep.logic.parser;

import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.UndoneCommand;


/**
 *
 * Parses input argument and creates a new UndoneCommand object
 *
 */
public class UndoneCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the UndoneCommand
     * and returns an UndoneCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }

        return new UndoneCommand(index.get());
    }
}
