package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.UncompleteCommand;

/**
 * Parses input arguments and creates a new UncompleteCommand object
 */
public class UncompleteCommandParser {
    //@@author A0163786N
    /**
     * Parses the given {@code String} of arguments in the context of the UncompleteCommand
     * and returns an UncompleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
        }

        return new UncompleteCommand(index.get());
    }

}
