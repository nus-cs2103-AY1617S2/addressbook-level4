package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UncompleteCommand;

public class UncompleteCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the UncompleteCommand
     * and returns an Command object for execution.
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
