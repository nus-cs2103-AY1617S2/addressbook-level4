package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.DeleteCommand;
import seedu.taskmanager.logic.commands.IncorrectCommand;

//@@author A0142418L
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;

        if (args.trim().matches(DeleteCommand.DELETE_COMMAND_VALIDATION_REGEX_1)) {
            Optional<Integer> index = ParserUtil.parseIndex(args);
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(index.get());
        } else {
            if (args.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(args.trim());
        }
    }
}
