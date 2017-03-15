package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.DeleteCommand;
import project.taskcrusher.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

}
