package t15b1.taskcrusher.logic.parser;

import static t15b1.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import t15b1.taskcrusher.logic.commands.Command;
import t15b1.taskcrusher.logic.commands.IncorrectCommand;
import t15b1.taskcrusher.logic.commands.SelectCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

}
