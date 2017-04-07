package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0142644J
/**
 * Parses input arguments and creates a new IncompleteCommand object
 */
public class IncompleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the IncompleteCommand
     * and returns an IncompleteCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncompleteCommand.MESSAGE_USAGE));
        }
        return new IncompleteCommand(index.get());
    }
}
