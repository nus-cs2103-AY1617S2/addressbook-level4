package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.DoneCommand;
import seedu.tasklist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a DoneCommand object
 * @author A0143355J
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
