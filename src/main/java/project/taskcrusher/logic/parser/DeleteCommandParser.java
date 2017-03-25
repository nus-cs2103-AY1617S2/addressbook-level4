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
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        String[] preamble = args.trim().split("\\s+");

        if (preamble.length != 2) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

            // TODO also place this somewhere central perhaps
        } else if (!preamble[0].matches(ListCommandParser.FLAG_TASK_OR_EVENT_VALIDATION_REGEX)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } else if (!preamble[1].matches("\\d+")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = ParserUtil.parseIndex(preamble[1]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(preamble[0], index.get());
    }

}
